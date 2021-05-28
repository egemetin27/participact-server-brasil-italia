package br.com.bergmannsoft.utils;

import org.springframework.data.domain.PageRequest; 
import org.springframework.data.domain.Pageable;

import br.com.bergmannsoft.config.Config; 
 
/**
 * Controle de paginacoes
 * @author Claudio
 *
 */
public class PaginationUtil {
	/**
	 * Retorna um PageResquest formatado
	 * @param offset
	 * @param limit
	 * @return
	 */
	public static PageRequest pagerequest(Integer offset, Integer limit) {
		if (offset == null || offset < Config.SELECT_MIN_OFFSET) {
			offset = Config.SELECT_DEFAULT_OFFSET;
		}
		if (limit == null || limit > Config.SELECT_MAX_COUNT) {
			limit = Config.SELECT_DEFAULT_COUNT;
		}
		return new PageRequest(offset - 1, limit);
	}
	/**
	 * Retorn um PageRequest como Pageable
	 * @param offset
	 * @param limit
	 * @return
	 */
	public static Pageable pageable(Integer offset, Integer limit) {
		return pagerequest(offset, limit);
	}
}
