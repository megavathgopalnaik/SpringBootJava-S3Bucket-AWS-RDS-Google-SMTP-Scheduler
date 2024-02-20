package rosahealthcarebackend.entity;

import java.io.ByteArrayInputStream;

public class ResetByteArray  extends ByteArrayInputStream{

	public ResetByteArray(byte[] buf) {
		super(buf);
		// TODO Auto-generated constructor stub
	}
    public void resetStream() {
    	this.pos=0;
    	this.mark=0;
    }
}
