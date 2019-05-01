# Data Communications (CO250) - Mini-Project

By - Niwedita (17CO227), Shweta Hariharan Iyer (17CO245)

EXPLORING RANDOM ACCESS AND HANDSHAKING TECHNIQUES IN UNDERWATER WIRELESS ACCOUSTIC NETWORKS

What is Random Access and Handshaking technique?
In Random Access Protocol, all stations have same superiority that is no station has more priority than another station. Any station can send data depending on medium’s state( idle or busy).
Carrier Sense Multiple Access ensures fewer collisions as the station is required to first sense the medium (for idle or busy) before transmitting data. If it is idle then it sends data, otherwise it waits till the channel becomes idle.

# Features of Random Access Protocol : 
1. There is no fixed time for sending data
2. There is no fixed sequence of stations sending data

# Features of Handshaking Protocol : 
It is also known as CSMA (Carrier Sense Multiple Access). It is subdivided into two -
1. CSMA/CD : Carrier sense multiple access with collision detection. Stations can terminate transmission of data if collision is detected.
2. CSMA/CA : Carrier sense multiple access with collision avoidance. Station waits for medium to become idle and if found idle it does not immediately send data (to avoid collision due to propagation delay) rather it waits for a period of time.
 
# Installation(Unetstack)
1.Download the appropriate package (tgz or zip) from the downloads page. Uncompress the package.
2.Run the IDE application by double-clicking on it. The IDE should open with the default “unetsim-1.4” folder as root.

# How to use?
1. Clone this repositary ( git clone https://github.com/Niwedita17/Aloha-with-RTS-CTS-technique.git )
2. Open Unetstack on your device
3. For Unetstack IDE, open handshake-sim.groovy. Click the Run button displayed above the code. A terminal-like window appears. Start executing RTS/CTS protocol there. For example : for sending a value 5 from node 1 to node 2, run the command - send 2, 5. Notice the working of RTS/CTS with Aloha protocol.
