package test.bcomparator;

import java.util.ArrayList;
import java.util.List;

/**
 * Results for one file: hashCodes for each block
 * @author sbelyak
 *
 */
public class FileResult {

	private long id;		// File id
	private String name;	// File name
	private int bs;		// Block size
	private long fs;		// File size (total)
	private List<byte[]> hashCodes;	// Hash codes for all blocks
	
	public FileResult() {
		hashCodes = new ArrayList<byte[]>();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBs() {
		return bs;
	}
	public void setBs(int bs) {
		this.bs = bs;
	}
	public long getFs() {
		return fs;
	}
	public void setFs(long fs) {
		this.fs = fs;
	}
	public List<byte[]> getHashCodes() {
		return hashCodes;
	}
	public void setHashCodes(List<byte[]> hashCodes) {
		this.hashCodes = hashCodes;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("name=").append(name)
				.append(", fs=").append(fs)
				.append(", bs=").append(bs)
				.append(", |hashCodes|=").append(hashCodes.size());
		return result.toString();
	}
	
}
