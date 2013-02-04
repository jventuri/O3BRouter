package com.va.snmpManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpManager
{
	private static Logger logger = Logger.getLogger(SnmpManager.class);
	
	private Snmp snmp;
	private String address;

	/**
	 * Constructor for Manager.
	 * 
	 * @param device
	 *           address of the form 'protocol:IP/port'. Example:
	 *           "udp:128.254.100.2/161"
	 */
	public SnmpManager(String address)
	{
		super();

		this.address = address;

		try
		{
			initialize();
		} catch (IOException e)
		{
			logger.error("IOException in SnmpManager constructor: " + e.getMessage());
			throw new RuntimeException(e);
		}

	}

	public void initialize() throws IOException
	{
		// define UDP transport mapping
		TransportMapping transport = new DefaultUdpTransportMapping();

		// core SNMP class for sending/receiving PDU's
		snmp = new Snmp(transport);

		// listen for incoming messages
		transport.listen();
	}

	public void stop() throws IOException
	{
		snmp.close();
	}

	private void bindEventsToListener()
	{

	}

	// Representation of a remote SNMP device
	private Target getTarget(String community)
	{

		// SNMP generic transport address
		Address targetAddress = GenericAddress.parse(address);

		// Target properties for community based messaging
		CommunityTarget target = new CommunityTarget();

		target.setCommunity(new OctetString(community)); // was va-lab

		// target address
		target.setAddress(targetAddress);

		// number of retries before a timeout
		target.setRetries(3);

		// timeout in ms before resending or timing out
		target.setTimeout(10000); // was 1500

		// SNMP version (processing model) to use
		target.setVersion(SnmpConstants.version2c);

		return target;
	}

	/**
	 * Get an OID value as a string via a synchronous call
	 * 
	 * @param oidStrings
	 *           - array of OID strings to get values for
	 * @param community
	 *           - community
	 * @return
	 * @throws IOException
	 */
	public String getAsString(String oidString, String community)
			throws IOException
	{
		String[] oids = new String[1];
		oids[0] = oidString;

		Map<String, String> valueMap = getAsString(oids, community);
		if (valueMap.size() > 0)
		{
			Object[] objects = valueMap.values().toArray();
			String val = (String) objects[0];

			return val;
		} else
		{
			throw new RuntimeException("Response is empty");
		}

	}

	/**
	 * Get OID values as strings via synchronous call
	 * 
	 * @param oids
	 * @return Map<String,String> of OID key value pairs
	 */
	public Map<String, String> getAsString(String[] oidStrings, String community)
			throws IOException
	{
		HashMap<String, String> oidValueMap = new HashMap<String, String>();

		OID[] oids = new OID[oidStrings.length];
		for (int i = 0; i < oidStrings.length; i++)
		{
			oids[i] = new OID(oidStrings[i]);
		}

		ResponseEvent event = get(oids, community);
		if (event.getResponse() != null)
		{
			PDU response = event.getResponse();
			for (int i2 = 0; i2 < response.size(); i2++)
			{
				String val = response.get(i2).getVariable().toString();
				String key = response.get(i2).getOid().toString();

				oidValueMap.put(key, val);
			}

			return oidValueMap;
		}

		throw new RuntimeException("Response is null");
	}

	/**
	 * Get OID values as strings via synchronous call
	 * 
	 * @param oids
	 * @param community
	 * @return ResponseEvent from SNMP device
	 */
	public ResponseEvent get(OID oids[], String community) throws IOException
	{
		ResponseEvent event = snmp.send(getPDU(oids), getTarget(community), null);
		if (event != null)
		{
			return event;
		}
		throw new RuntimeException("GET timed out");
	}

	/**
	 * Get OID values as strings via asynchronous messaging
	 * 
	 * @param oids
	 * @param listener
	 *           - called when the PDU is a confirmed PDU and the request is
	 *           either answered or timed out
	 * @param community
	 */
	public void getAsString(OID[] oids, ResponseListener listener,
			String community)
	{

		try
		{
			snmp.send(getPDU(oids), getTarget(community), null, listener);
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}

	}

	/**
	 * Creates an SNMP PDU (Protocol Data Unit) of type GET from an array of OID
	 * objects.
	 * 
	 * @param oids
	 *           - array of OID (Object Identifier class representing data
	 *           identifier in a MIB)
	 */
	private PDU getPDU(OID oids[])
	{
		PDU pdu = new PDU();
		for (OID oid : oids)
		{
			// add variable binding (association of a OID and the instance's value)
			pdu.add(new VariableBinding(oid));
		}

		pdu.setType(PDU.GET);
		return pdu;
	}

	public static Vector<? extends VariableBinding> extractVariableBindings(
			ResponseEvent event)
	{
		Vector<? extends VariableBinding> vbVector = null;

		if (event.getResponse() != null)
		{
			vbVector = event.getResponse().getVariableBindings();
			Iterator<? extends VariableBinding> itr = vbVector.iterator();

			// Iterate thru all the variable bindings and display the results
			while (itr.hasNext())
			{
				VariableBinding vb = itr.next();
				String oidStr = vb.getOid().toString();
				String variableStr = vb.getVariable().toString();

				System.out.println("OID '" + oidStr + "' = " + variableStr);
			}

			return vbVector;
		}

		throw new RuntimeException("Response is null");
	}

	/**
	 * Inner class that listens for a response from the device.
	 * 
	 */
	class StringResponseListener implements ResponseListener
	{
		// TODO this inner class should be in the client of SnmpManager, not here!
		// Client needs to deal with the event.
		private String value = null;

		@Override
		public void onResponse(ResponseEvent event)
		{
			System.out.println(event.getResponse());
			if (event.getResponse() != null)
			{
				SnmpManager.extractVariableBindings(event);
			} else
			{
				System.err.println("Event is null");
			}
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		SnmpManager manager;
		String community = null;

		try
		{
			// Note: default UDP port for SNMP is 161
			if (args.length < 3)
			{
				System.out.println("Required arguments are:");
				System.out
						.println("Argument #1: device address of the form 'protocol:IP/port'. Example: 'udp:128.254.100.2/161'");
				System.out.println("Arguments #2: Community");
				System.out.println("Arguments #3...n: OID");
			} else
			{
				// Setup the client to use our newly started agent
				manager = new SnmpManager(args[0]);
				community = args[1];

				final StringResponseListener listener = manager.new StringResponseListener();

				String[] oids = new String[args.length - 2];
				int idx = 0;
				for (int i = 2; i < args.length; i++)
				{
					oids[idx++] = args[i];
				}

				// asynchronous GET
				// manager.getAsString(oids, listener);

				// Synchronous GET
				Map<String,String> valueMap = manager.getAsString(oids, community);
				
				for (String key : valueMap.keySet()) 
				{
					System.out.println("OID:" + key + "      Value:" + valueMap.get(key));
				}

				// manager.stop();
			}
		} catch (Exception e) // was IOException
		{
			System.err.println("Exception thrown: " + e.getMessage());
		}

	}

}
