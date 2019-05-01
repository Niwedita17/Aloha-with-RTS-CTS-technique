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
send = { addr, value ->
  println "sending $value to node $addr"
  phy << new DatagramReq(to: addr, protocol: Protocol.DATA, data: value)
  def txNtf = receive(TxFrameNtf, 1000)
  def rxNtf = receive({ it instanceof RxFrameNtf && it.from == addr}, 5000)
    if (txNtf && rxNtf && rxNtf.from == addr)
      println "Data Received at ${rxNtf.to} from ${rxNtf.from} is: ${rxNtf.data}"  
}
