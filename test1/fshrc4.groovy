import org.arl.unet.*
import org.arl.unet.phy.*
import org.arl.unet.*
import org.arl.unet.phy.*
import org.arl.unet.mac.*
//import org.arl.unet.nodeinfo.NodeInfo
//import org.arl.unet.PDU 
import org.arl.fjage.*
//import static org.arl.unet.Services.*
//import static org.arl.unet.phy.Physical.*


subscribe phy

// add a closure to define the 'ping' command
sendl = { addr, value ->
  println "sending RTS to node $addr"
  phy << new ReservationReq(recipient: mac, to: 2, duration: 5.second)//not sure about the syntax
  def msg = receive(RxFrameNtf, 1000)
  def rxNtf = receive({ it instanceof RxFrameNtf && it.from == addr}, 5000)
  def rx = pdu.decode(msg.data)
  if(rx.type == CTS_PDU && rxNtf && rxNtf.from == addr)
  {
    println "CTS Received at ${rxNtf.to} from ${rxNtf.from}}"
    phy << new DatagramReq(to: addr, protocol: Protocol.DATA, data: value)
  } 
}
