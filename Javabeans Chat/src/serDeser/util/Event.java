package serDeser.util;

@SuppressWarnings("serial")
public class Event extends MyBean {
    private String message;
    private Integer sequenceNumber;
    private String timeStamp;
    
    public Event()
    {
    }
    
    public Event(String message, int sequenceNumber, String timeStamp)
    {
    	this.message = message;
    	this.sequenceNumber = new Integer(sequenceNumber);
    	this.timeStamp = timeStamp;
    }
    
    public String toString()
    {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Message: " + message + "\n");
    	sb.append("Sequence Number: " + sequenceNumber + "\n");
    	sb.append("Timestamp: " + timeStamp + "\n");
    	return sb.toString();
    }
    
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public Integer getSequenceNumber() {
		return sequenceNumber;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
}