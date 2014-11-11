package test.bcomparator;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.log4j.Logger;


public class Hasher implements Runnable {

	private Coordinator coordinator;
	private MessageDigest m;
	
	private static final Logger LOG = Logger.getLogger(Hasher.class);
	
	public Hasher(Coordinator coordinator) {
		this.coordinator = coordinator;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		FileResult fileResult = coordinator.getNextFile();;
		 while(null != fileResult) {
			try {
				hashFile(fileResult);
				coordinator.storeResult(fileResult);
				fileResult = coordinator.getNextFile();
			} catch (IOException e) {
				LOG.error("Error while processing file " + fileResult.getName());
			}
			
		}
		
	}
	
	private void hashFile(FileResult fileResult) throws IOException {
		
		File f = new File(fileResult.getName());
		
		byte buffer[] = new byte[fileResult.getBs()];
		int readCounter = 0;
		if (f.exists() && f.canRead() && f.isFile()) {
			
			BufferedInputStream bf = new BufferedInputStream(new FileInputStream(f));
			try {
				while (-1 != readCounter) {
					readCounter = bf.read(buffer);
					
					if (readCounter != buffer.length) {
						if (-1 == readCounter) {
							break;
						}
						Arrays.fill(buffer, readCounter, buffer.length-1, (byte)0);
					}
					
					byte[] blockHash = hashBytes(buffer);
					fileResult.getHashCodes().add(blockHash);
				}
				fileResult.setFs(f.length());
			} finally {
				bf.close();
			}
		} else {
			LOG.error("File " + fileResult.getName() + " can't be processed!");
		}
			
		
		
	}
	
	public byte[] hashBytes(byte[] bytes) {
		m.reset();
		m.update(bytes);
		return m.digest();
	}
}
