//! Simulation: 3-node network with ping daemons

import org.arl.fjage.Message
import org.arl.unet.*
import org.arl.mac.*
import org.arl.unet.phy.*

import org.arl.fjage.RealTimePlatform

println '''
4-node network with handshake technique
-----------------------------------------
Node 1 or Node 3 or Node 4 will send a RTS to node 2
The agent MyHandshakeMac present at node 2, will check the state of msg recieved, as it should be RTS, then it will send CTS to the sender node address and reserve the channel till timeout period(5sec)
Sender node will recieve CTS and then it will send value to node 2.

When you are done, exit the shell by pressing ^D or entering:
> shutdown
'''

platform = RealTimePlatform

// run simulation forever
simulate {
  node '1', address: 1, remote: 1101, location: [0, 0, 0], shell: true, stack: { container ->
    container.add 'hand', new MySimpleHandshakeMac()
    container.shell.addInitrc "${script.parent}/fshrc.groovy"
  }
  node '2', address: 2, remote: 1102, location: [1.km, 0, 0], shell:5102, stack: { container ->
    container.add 'hand', new MySimpleHandshakeMac()
  }
  /*node '3', address: 3, remote: 1103, location: [2.km, 0, 0], shell: 5103, stack: { container ->
    container.add 'hand', new MySimpleHandshakeMac()
    container.shell.addInitrc "${script.parent}/fshrc3.groovy"
  }
  node '4', address: 4, remote: 1104, location: [3.km, 0, 0], shell: 5104, stack: { container ->
    container.add 'hand', new MySimpleHandshakeMac()
    container.shell.addInitrc "${script.parent}/fshrc4.groovy"
  }*/
}