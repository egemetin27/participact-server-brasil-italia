package it.unibo.paserver.web.controller;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.AzureStorage;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import com.opencsv.CSVWriter;
import it.unibo.paserver.domain.FileUpload;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.StorageFileImage;
import it.unibo.paserver.domain.support.FileBuilder;
import it.unibo.paserver.service.FileImportService;
import it.unibo.paserver.service.FileUploadService;
import it.unibo.paserver.web.security.v1.AccountAdminDetails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.Iterator;

@SuppressWarnings("Duplicates")
@Controller
public class FileController {
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    FileImportService fileImportService;
    @Autowired
    private MessageSource messageSource;

    /**
     * @param fileSource
     * @param request
     * @return
     */
    @RequestMapping(value = "/protected/file-upload", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson uploadFile(@RequestParam("fileSource") String fileSource, MultipartHttpServletRequest request) {
        // Id do usuario que subiu o arquivo
        AccountAdminDetails current = (AccountAdminDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long parentId = current.getId();
        fileSource = !Validator.isEmptyString(fileSource) ? fileSource : "N/A";
        // Response
        ResponseJson response = new ResponseJson();
        try {
            Iterator<String> itr = request.getFileNames();
            while (itr.hasNext()) {
                String uploadedFile = itr.next();
                MultipartFile file = request.getFile(uploadedFile);
                String mimeType = file.getContentType();
                String filename = file.getOriginalFilename();
                if (Validator.isValidMimeTypeCSV(mimeType)) {
                    byte[] bytes = file.getBytes();

                    FileBuilder fb = new FileBuilder();
                    fb.setAll(0, parentId, filename, bytes, mimeType, (byte) 0, fileSource);
                    fb.setAdmin(true);
                    FileUpload f = fb.build(true);
                    // Save
                    FileUpload rs = fileUploadService.saveOrUpdate(f);
                    if (rs != null) {
                        //Response
                        response.setItem(rs);
                        response.setStatus(true);
                        response.setMessage(messageSource.getMessage("confirmation.importing", null, LocaleContextHolder.getLocale()));
                        //Save to disk
                        File dest = new File(Config.PRODUCTION_STORAGE_TEMP + Useful.secureRandomString() + ".csv");
                        file.transferTo(dest);
                        f.setUploadedFile(dest);
                        // fileImportService.importUsersByFile(f);
                        fileImportService.importUsersByScript(f);
                    }
                } else {
                    response.setMessage(messageSource.getMessage("error.file.format", null, LocaleContextHolder.getLocale()));
                    break;
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * @param response
     */
    @RequestMapping(value = "/protected/example/participant", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
    public void exampleParticipant(HttpServletResponse response) {
        String csvFileName = "ParticipAct_Exemplo.csv";
        response.setContentType("text/csv");
        try {
            OutputStream os = response.getOutputStream();

            // creates mock data
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
            response.setHeader(headerKey, headerValue);

            String[] header = {"name", "surname", "email", "password", "birthdate", "currentAddress", "currentCity", "currentZipCode", "currentNumber", "currentProvince", "currentCountry", "mapLat", "mapLng", "contactPhoneNumber",
                    "device", "notes"};
            String[] e1 = {"Fulano", "Santos", "fulano@example.com", "password", "2016-01-01", "Avenida Madre Benvenuta - Trindade", "Florianopolis", "88034000", "S/N", "Santa Catarina", "Brasil", "-27.6058242", "-48.5975519,13",
                    "(48) 3664-8200", "Nexus X", "Aluno Regular"};
            String[] e2 = {"Beltrano", "Oliveira", "beltrano@example.com", "password", "2016-01-01", "Avenida Madre Benvenuta - Trindade", "Florianopolis", "88034000", "1", "Santa Catarina", "Brasil", "-27.6058242", "-48.5975519,13",
                    "(48) 3664-8200", "Android Z", "Comunidade"};
            String[] e3 = {"Ciclano", "Souza", "ciclano@example.com", "password", "2016-01-01", "Avenida Madre Benvenuta - Trindade", "Florianopolis", "88034000", "500", "Santa Catarina", "Brasil", "-27.6058242", "-48.5975519,13",
                    "(48) 3664-8200", "Ubuntu U", "Observa��es adicionais"};

            CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(os));
            csvWriter.writeNext(header);
            csvWriter.writeNext(e1);
            csvWriter.writeNext(e2);
            csvWriter.writeNext(e3);
            csvWriter.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param uploadFile
     * @param request
     * @return
     */
    @RequestMapping(value = "/protected/storage-upload", method = RequestMethod.POST, headers = {"content-type=multipart/mixed", "content-type=multipart/form-data"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN','ROLE_RESEARCHER_OMBUDSMAN_EDITOR','ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR')")
    public @ResponseBody
    ResponseJson uploadStorage(@RequestParam("filename") MultipartFile uploadFile, MultipartHttpServletRequest request) {
        // Id do usuario que subiu o arquivo
        AccountAdminDetails current = (AccountAdminDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long parentId = current.getId();
        // Response
        ResponseJson response = new ResponseJson();
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        try {
            String originalFilename = uploadFile.getOriginalFilename();
            String ext = FilenameUtils.getExtension(originalFilename);
            String name = Useful.secureRandomString();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String filename = String.format("%d_I%s_%s.%s", parentId, timestamp.getTime(), name, ext);
            String assetUrl = String.format("%s/%s", Config.PRODUCTION_STORAGE_URL, filename);
            // Storage File Img
			StorageFileImage img = new StorageFileImage();
			img.setAssetUrl(assetUrl);
			img.setFileName(filename);
			img.setFileExtension(ext);
			img.setOriginalFilename(originalFilename);
            // File
            File file = new File(Config.PRODUCTION_STORAGE_TEMP + filename);
            uploadFile.transferTo(file);
            // Azure
            AzureStorage.uploadBlod(file, null);
            // Response
            response.setStatus(true);
            response.setItem(assetUrl);
            response.setStdClass(img);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
