/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager;

import FanManager.view.RootLayoutController;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 *
 * @author Reign
 */
    public class MyRxTx implements SerialPortEventListener, Runnable { 

        SerialPort serialPort;
        /**
         * The port we're normally going to use.
         */
        private final String PORT_NAMES[] = {
            "/dev/ttyACM0", //for Ubuntu
            "/dev/cu.MyBluetooth-SPPDEv", // Mac OS X
            "/dev/ttyUSB0", // Linux
            "COM3", // Windows
        };
        private BufferedReader input;
        private OutputStream output;
        private static final int TIME_OUT = 2000;
        private static final int DATA_RATE = 115200;
        private RootLayoutController rThis;
    
        
    public MyRxTx(RootLayoutController aThis) {
        this.rThis = aThis;
                        System.out.println("Inside aThis RxTx!");

    
    }

    @Override
    public void run() {
                        System.out.println("Running RxTx!");

            CommPortIdentifier portId = null;
            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

            //First, Find an instance of serial port as set in PORT_NAMES.
            while (portEnum.hasMoreElements()) {
                CommPortIdentifier currPortId
                        = (CommPortIdentifier) portEnum.nextElement();
                for (String portName : PORT_NAMES) {
                    if (currPortId.getName().equals(portName)) {
                        portId = currPortId;
                        break;
                    }
                }
            }
            if (portId == null) {
                System.out.println("Could not find COM port.");
                return;
            } else {
                System.out.println("Port Name: " + portId.getName() + "\n"
                        + "Current Owner: " + portId.getCurrentOwner() + "\n"
                        + "Port Type: " + portId.getPortType());
            }

            try {
                // open serial port, and use class name for the appName.
                serialPort = (SerialPort) portId.open(this.getClass().getName(),
                        TIME_OUT);

                // set port parameters
                serialPort.setSerialPortParams(DATA_RATE,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);

                // open the streams
                input = new BufferedReader(
                        new InputStreamReader(serialPort.getInputStream()));
                output = serialPort.getOutputStream();

                // add event listeners
                serialPort.addEventListener(this);
                serialPort.notifyOnDataAvailable(true);
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }

        @Override
        public void serialEvent(SerialPortEvent spe) {
            if (spe.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                try {
                    final String inputLine = input.readLine();
                    System.out.println(inputLine);
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
        }

        /**
         * This should be called when you stop using the port. This will prevent
         * port locking on platforms like Linux.
         */
        public synchronized void close() {
            if (serialPort != null) {
                System.out.println("RxTx Close!");

                serialPort.removeEventListener();
                serialPort.close();
            }
        }

    }