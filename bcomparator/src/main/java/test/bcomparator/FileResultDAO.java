package test.bcomparator;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;


public class FileResultDAO {

	private static final Logger LOG = Logger.getLogger(FileResultDAO.class);
	
	public FileResult addResult(FileResult fileResult, SqlMapClient sqlMapClient) {
		FileResult result = null;
		try {
			Long id = (Long)sqlMapClient.queryForObject("fileResult.addResult", fileResult);
			id = (null == id)?1:id+1;
			fileResult.setId(id);
			result = fileResult;
		} catch (Exception e) {
			LOG.error("Error while creating new fileResult into DB", e);
		}
		return result;
	}
}
