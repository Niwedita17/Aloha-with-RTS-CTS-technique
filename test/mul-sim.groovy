//! Simulation: 3-node network with ping daemons

import org.arl.fjage.Message
import org.arl.unet.*
import org.arl.mac.*
import org.arl.unet.phy.*

import org.arl.fjage.RealTimePlatform

println '''
2-node network with multiplication daemon
-----------------------------------------
Node 1 will send a value to node 2
The agent MulAgent present at node 2, will multiply by 2 to received data and send the value to node 1

You can interact with node 1 in the console shell. For example, try:
> send <to-address> , <data>
For example:
> send 2, 7

When you are done, exit the shell by pressing ^D or entering:
> shutdown
'''

platform = RealTimePlatform

// run simulation forever
simulate {
  node '1', address: 1, remote: 1101, location: [0, 0, 0], shell: true, stack: { container ->
    container.add 'mul', new MulAgent()
    container.shell.addInitrc "${script.parent}/fshrc.groovy"
  }
  node '2', address: 2, remote: 1102, location: [1.km, 0, 0], shell:5102, stack: { container ->
    container.add 'mul', new MulAgent()
  }
  node '3', address: 3, remote: 1103, location: [2.km, 0, 0], shell: 5103, stack: { container ->
    container.add 'hand', new MySimpleHandshakeMac()
    //container.shell.addInitrc "${script.parent}/fshrc.groovy"
  }
  node '4', address: 4, remote: 1104, location: [3.km, 0, 0], shell: 5104, stack: { container ->
    container.add 'hand', new MySimpleHandshakeMac()
    //container.shell.addInitrc "${script.parent}/fshrc.groovy"
  }
}
