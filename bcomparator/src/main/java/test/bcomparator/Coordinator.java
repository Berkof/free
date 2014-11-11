package test.bcomparator;

import java.io.File;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.lf5.util.Resource;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class Coordinator {

	private AtomicInteger curId;
	private File[] filesToHash;
	
	FileResultDAO frd;
	SqlMapClient sqlMapClient;
	
	public FileResult getNextFile() {
		int id = curId.getAndIncrement();
		FileResult result = null;
		if (id < filesToHash.length) {
			result = new FileResult();
			result.setName(filesToHash[id].getPath());
			result.setBs(64*1024);
		}
		return result;
	}
	
	public long getCurrentBS() {
		return 64L*1024L;
	}
	
	public void start(String dirname) throws Exception {
		File inputDir = new File(dirname);
		if (!inputDir.exists() || !inputDir.canRead() || !inputDir.isDirectory()) {
			throw new Exception("Can't read directory " + dirname);
		}
		filesToHash = inputDir.listFiles();
		curId = new AtomicInteger(0);
		
		
		frd = new FileResultDAO();
		Reader reader = Resources.getResourceAsReader("sqlmaps.xml");
		
		sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
		
		int htc = 4;
		Thread threads[] = new Thread[htc]; 
		for (int i =0; i<htc;i++) {
			Hasher h = new Hasher(this);
			Thread t = new Thread(h);
			threads[i] = t;
			t.start();
		}
		for(int i=0;i<htc;i++) {
			threads[i].join();
		}
		
	}
	
	public synchronized void storeResult(FileResult result) {
		System.out.println("saving " + result);
		frd.addResult(result, sqlMapClient);
	}
	
}
