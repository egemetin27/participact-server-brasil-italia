package it.unibo.paserver.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import br.com.bergmannsoft.utils.Useful;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskGeoDrawing;
import it.unibo.paserver.domain.TaskGeoSpherical;
import it.unibo.paserver.domain.support.TaskGeoDrawingBuilder;
import it.unibo.paserver.service.TaskGeoDrawingService;

/**
 * Desenhos geograficos/mapas
 * 
 * @author Claudio
 *
 */
@Controller
public class CampaignTaskGeoDrawing extends ApplicationController {
	@Autowired
	private TaskGeoDrawingService taskGeoDrawingService;

	private boolean hasCenter;
	private boolean hasGeometry;
	/**
	 * Salva/Atualiza um item
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = { "/protected/campaign-task-geo-drawing/save/" }, method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
	public @ResponseBody ResponseJson save(@RequestBody String json, HttpServletRequest request) {
		// Response
		ResponseJson response = new ResponseJson();
		response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
		// Request
		ReceiveJson r = new ReceiveJson(json);
		// Values
		long campaign_id = Useful.convertStringToLong(r.getAsString("campaign_id"));
		// Check
		isCheck(campaign_id);
		// Security
		isRoot(request);
		//Validate
		if (!isPublish||isAdmin) {
			boolean isActivation = r.getAsBoolean("isActivation");
			boolean isNotification = r.getAsBoolean("isNotification");
			JsonArray consolidated = r.getAsJsonArray("consolidated");
			if (!consolidated.isJsonArray() || consolidated.size() < 1) {
				response.setMessage(messageSource.getMessage("error.task.consolidated.required", null, LocaleContextHolder.getLocale()));
			} else {
				hasCenter = false;
				hasGeometry = false;
				List<TaskGeoSpherical> geoCenter = new ArrayList<TaskGeoSpherical>();
				List<TaskGeoSpherical> geoSpherical = new ArrayList<TaskGeoSpherical>();
				String type = null;
				Double radius = null;
				String guid = null;
				// WKT
				String wellKnownText = "POLYGON(";
				// loop
				for (JsonElement elm : consolidated) {
					// Json
					if (elm.isJsonObject()) {
						JsonObject drawing = elm.getAsJsonObject();
						// Object
						if (drawing.has("center") && drawing.has("radius") && drawing.has("spherical") && drawing.has("type")) {
							type = drawing.get("type").getAsString();
							radius = drawing.get("radius").getAsDouble();
							guid = drawing.get("guid").getAsString();
							JsonObject center = drawing.get("center").getAsJsonObject();
							JsonArray spherical = drawing.get("spherical").getAsJsonArray();
							// Geo
							if (center.has("lat") && center.has("lng") && spherical.size() > 0) {
								hasCenter = true;
								geoCenter.add(new TaskGeoSpherical(guid, "center", center.get("lat").getAsString(), center.get("lng").getAsString(), type, radius, true));
								// WKT
								wellKnownText += "(";
								// Geometrias
								String first = null;
								for (JsonElement sph : spherical) {
									// eh um objeto?
									if (sph.isJsonObject()) {
										JsonObject erical = sph.getAsJsonObject();
										if (erical.has("key") && erical.has("lat") && erical.has("lng")) {
											hasGeometry = true;
											TaskGeoSpherical element = new TaskGeoSpherical(guid,erical.get("key").getAsString(), erical.get("lat").getAsString(),erical.get("lng").getAsString(), type, radius, false);
											geoSpherical.add(element);
											// WKT
											wellKnownText += erical.get("lat").getAsString() + " " + erical.get("lng").getAsString() + ",";
											if (first == null) {
												first = erical.get("lat").getAsString() + " " + erical.get("lng").getAsString() + ",";
											}
										}
									}
								}
								wellKnownText += first;
								// WKT
								wellKnownText = StringUtils.removeEnd(wellKnownText, ",");
								// WKT
								wellKnownText += "),";
							}
						}
					}
				}
				// WKT
				wellKnownText = StringUtils.removeEnd(wellKnownText, ",");
				// WKT
				wellKnownText += ")";				
				//System.out.println(wellKnownText.toString());
				// Salvando
				if (hasCenter && hasGeometry && geoSpherical.size() > 0) {
					// Campaign
					Task task = campaignService.findById(campaign_id);
					if (task != null) {
						boolean removed = taskGeoDrawingService.deleteByTaskIdType(campaign_id, isNotification);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace(System.out);
						}
						Set<TaskGeoSpherical> setCenter = new HashSet<TaskGeoSpherical>(geoCenter);
						Set<TaskGeoSpherical> setSpherical = new HashSet<TaskGeoSpherical>(geoSpherical);
						TaskGeoDrawingBuilder drawingManager = new TaskGeoDrawingBuilder();
						drawingManager.setAll(0L, radius, type, setSpherical, setCenter, campaign_id, isNotification, isActivation);
						TaskGeoDrawing atLongLast = drawingManager.build(true);
						TaskGeoDrawing rs = taskGeoDrawingService.saveOrUpdate(atLongLast);
						if (rs != null) {
							if (isActivation) {
								task.setActivationArea(wellKnownText);
							} else if (isNotification) {
								task.setNotificationArea(wellKnownText);
							}
							taskService.save(task);
							// status
							response.setStatus(true);
						}
					}
				}
			}
		}
		// Result
		return response;
	}

	/**
	 * Copiando referencia de uma area para outra
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = { "/protected/campaign-task-geo-drawing/copy/" }, method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
	public @ResponseBody ResponseJson copy(@RequestBody String json, HttpServletRequest request) {
		// Response
		ResponseJson response = new ResponseJson();
		response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
		// Request
		ReceiveJson r = new ReceiveJson(json);
		// Values
		long campaign_id = Useful.convertStringToLong(r.getAsString("campaign_id"));
		// Check
		isCheck(campaign_id);
		// Security
		isRoot(request);
		//Validate
		if (!isPublish||isAdmin) {
			boolean isNotification = r.getAsBoolean("isNotification");
			boolean isActivation = !isNotification;// Invertemos a flag
			// Campaign
			Task task = campaignService.findById(campaign_id);
			// Drwan
			TaskGeoDrawing drawing = taskGeoDrawingService.findByTaskIdType(campaign_id, isNotification);
			if (task != null && drawing != null) {
				// Copy polygon
				if (isNotification) {
					task.setActivationArea(task.getNotificationArea());
					taskGeoDrawingService.deleteByTaskIdType(campaign_id, isActivation);
				} else {
					task.setNotificationArea(task.getActivationArea());
					taskGeoDrawingService.deleteByTaskIdType(campaign_id, isActivation);
				}
				Task co = taskService.save(task);
				// Limpando e inicilizando novos objetos
				Set<TaskGeoSpherical> center = drawing.getCenter();
				Set<TaskGeoSpherical> spherical = drawing.getSpherical();
				Set<TaskGeoSpherical> setCenter = new HashSet<TaskGeoSpherical>();
				Set<TaskGeoSpherical> setSpherical = new HashSet<TaskGeoSpherical>();
				for (TaskGeoSpherical sph : center) {
					setCenter.add(new TaskGeoSpherical(sph.getGuid(), sph.getKey(), sph.getLat(), sph.getLng(),
							sph.getType(), sph.getRadius(), sph.isCenter()));
				}
				for (TaskGeoSpherical sph : spherical) {
					setSpherical.add(new TaskGeoSpherical(sph.getGuid(), sph.getKey(), sph.getLat(), sph.getLng(),
							sph.getType(), sph.getRadius(), sph.isCenter()));
				}
				// Copy Geo
				TaskGeoDrawingBuilder drawingManager = new TaskGeoDrawingBuilder();
				drawingManager.setAll(0L, drawing.getRadius(), drawing.getType(), setSpherical, setCenter, campaign_id,
						!isNotification, !isActivation);// Invertemos as flag,
														// pois
														// estamos copiando de
														// uma
														// area para outra
				TaskGeoDrawing atLongLast = drawingManager.build(true);
				TaskGeoDrawing py = taskGeoDrawingService.saveOrUpdate(atLongLast);
				if (co != null && py != null) {
					// Return
					response.setStatus(true);
					response.setMessage(messageSource.getMessage("confirmation.copy.success", null, LocaleContextHolder.getLocale()));
				}
			} else {
				response.setMessage(messageSource.getMessage("confirmation.copy.fail", null, LocaleContextHolder.getLocale()));
			}
		}
		// Return
		return response;
	}

	/**
	 * Removendo refencias de geo
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = { "/protected/campaign-task-geo-drawing/removed/" }, method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
	public @ResponseBody ResponseJson removed(@RequestBody String json, HttpServletRequest request) {
		// Response
		ResponseJson response = new ResponseJson();
		response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
		// Request
		ReceiveJson r = new ReceiveJson(json);
		// Values
		long campaign_id = Useful.convertStringToLong(r.getAsString("campaign_id"));
		// Check
		isCheck(campaign_id);
		// Security
		isRoot(request);
		//Validate
		if (!isPublish||isAdmin) {
			boolean isNotification = r.getAsBoolean("isNotification");
			// Campaign
			Task task = campaignService.findById(campaign_id);
			if (task != null) {
				if (isNotification) {
					task.setNotificationArea(null);
				} else {
					task.setActivationArea(null);
				}
				taskService.save(task);
				taskGeoDrawingService.deleteByTaskIdType(campaign_id, isNotification);
				response.setStatus(true);
				response.setMessage(messageSource.getMessage("confirmation.remove.success", null, LocaleContextHolder.getLocale()));
			}
		}
		// Return
		return response;
	}

	/**
	 * Retorna todos os items de acordo com id principal
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/protected/campaign-task-geo-drawing/find", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT', 'ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')")
	public @ResponseBody ResponseJson find(@RequestBody String json) {
		// Response
		ResponseJson response = new ResponseJson();
		// Values
		ReceiveJson r = new ReceiveJson(json);
		long campaign_id = Useful.convertStringToLong(r.getAsString("campaign_id"));
		boolean isNotification = r.getAsBoolean("isNotification");
		// Search
		List<TaskGeoDrawing> drawing = taskGeoDrawingService.findAllByTaskIdType(campaign_id, isNotification);
		if (drawing != null) {
			response.setStatus(true);
			response.setItem(drawing);
		}
		return response;
	}
}
