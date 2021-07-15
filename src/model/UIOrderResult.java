package model;

 /*
   This class represents results of orders given from view part of application
   results contain a boolean value and a message.
  */
public class UIOrderResult {
        String message;
        boolean done;

        public UIOrderResult(String message, boolean done) {
            this.message = message;
            this.done = done;
        }

        public String getMessage() {
            return message;
        }
        public boolean isDone() {
            return done;
        }

}
