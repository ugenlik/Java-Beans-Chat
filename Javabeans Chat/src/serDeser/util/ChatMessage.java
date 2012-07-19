package serDeser.util;

@SuppressWarnings("serial")
public class ChatMessage extends MyBean {
    private String message;
    private String sender;
    private String buddyName;
    private String timeStamp;
    
    public ChatMessage()
    {
    }
    
    public ChatMessage(String message, String sender, String buddyName, String timeStamp)
    {
    	this.message = message;
    	this.sender = sender;
    	this.buddyName = buddyName;
    	this.timeStamp = timeStamp;
    }
    
    public String toString()
    {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Message: " + message + "\n");
    	sb.append("Sender: " + sender + "\n");
    	sb.append("Buddy Name: " + buddyName + "\n");
    	sb.append("Timestamp: " + timeStamp + "\n");
    	return sb.toString();
    }
    
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSender() {
		return sender;
	}
	public void setBuddyName(String buddyName) {
		this.buddyName = buddyName;
	}
	public String getBuddyName() {
		return buddyName;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
}