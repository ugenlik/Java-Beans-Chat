package serDeser.driver;

import java.util.Calendar;
import serDeser.genericSerDeser.JavaBeanProcessor;
import serDeser.util.Event;
import serDeser.util.ChatMessage;
import serDeser.util.Mesh;
import serDeser.util.MyBean;

public class Driver {

	/**
	 * @param args none
	 */
	public static void main(String[] args) {
		String timeStamp = Calendar.getInstance().getTime().toString();
		
		// create objects
		ChatMessage object1 = new ChatMessage("I love you!", "Cenk", "Nesil", timeStamp);
		Event 		object2 = new Event("An Event Ocurred", 1, timeStamp);
		Mesh 		object3 = new Mesh(3, 5, 3.14);
		
		// serialize objects
		String object1str = JavaBeanProcessor.serializeBean(object1, "xmlstd");
		String object2str = JavaBeanProcessor.serializeBean(object2, "xmlnew");
		String object3str = JavaBeanProcessor.serializeBean(object3, "xmlverbose");

		// deserialize objects
		MyBean bean1 = JavaBeanProcessor.deserializeBean(object1str);
		MyBean bean2 = JavaBeanProcessor.deserializeBean(object2str);
		MyBean bean3 = JavaBeanProcessor.deserializeBean(object3str);
		
		// print serialized XML and deserialized object for each object
		System.out.println(object1str);
		System.out.println(bean1);
		
		System.out.println(object2str);
		System.out.println(bean2);
		
		System.out.println(object3str);
		System.out.println(bean3);
	}

}
