import org.arl.fjage.Message
import org.arl.unet.*
import org.arl.mac.*

class MulAgent extends UnetAgent {

  final static int PROTOCOL = Protocol.DATA
  int received_data
  int new_data

  void startup() {
    def phy = agentForService Services.PHYSICAL
    subscribe topic(phy)
   }

  void processMessage(Message msg) {
    if (msg instanceof DatagramNtf && msg.protocol == PROTOCOL)
    {
      received_data = msg.data[0];
      new_data = 2 * received_data;
      send new DatagramReq(recipient: msg.sender,to: msg.from, protocol: Protocol.MAC, data: new_data)
  	}
  }
}
