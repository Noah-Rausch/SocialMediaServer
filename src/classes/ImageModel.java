package classes;

import java.util.ArrayList;

//Model class to represent image transactions.  Has potential to model multiple senders,
// recipients, and image URLS.
public class ImageModel {
  byte[] imageBytes;
  private ArrayList<String> sender;
  private ArrayList<String> url;
  private ArrayList<String> recipient;
  private String responseMessage;

  public ImageModel(ArrayList<String> s, ArrayList<String> u, ArrayList<String> r){
      this.sender = s;
      this.url = u;
      this.recipient = r;
  }

  public ImageModel(){
      // Empty constructor
  }

  public byte[] getImageBytes() {
      return imageBytes;
  }

  public void setImageBytes(byte[] imageBytes) {
      this.imageBytes = imageBytes;
  }

  public ArrayList<String> getSender() {
      return sender;
  }
  public void setSender(ArrayList<String> sender) {
      this.sender = sender;
  }
  public ArrayList<String> getUrl() {
      return url;
  }
  public void setUrl(ArrayList<String> url) {
      this.url = url;
  }
  public ArrayList<String> getRecipient() {
      return recipient;
  }
  public void setRecipient(ArrayList<String> recipient) {
      this.recipient = recipient;
  }
  public String getResponseMessage() {
      return responseMessage;
  }
  public void setResponseMessage(String responseMessage) {
      this.responseMessage = responseMessage;
  }
}
