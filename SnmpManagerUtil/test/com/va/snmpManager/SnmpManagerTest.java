package com.va.snmpManager;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/*
 * Unit tests for SnmpManager
 */
public class SnmpManagerTest
{

	@Test
	public void testGetAsString()
	{

		// Setup the client to use our newly started agent
		// device address of the form 'protocol:IP/port'. Example:
		// 'udp:128.254.100.2/161'"

		/*
		 * Host: 128.254.128.1 Version: 2c Community: va-internal OID :
		 * 1.3.6.1.4.1.2636.3.3.1.1.8.510
		 */

		try
		{
			SnmpManager manager = new SnmpManager("udp:128.254.128.1/161");

			String value = manager.getAsString(".1.3.6.1.4.1.2636.3.3.1.1.8.510",
					"va-internal");
			
			manager.stop();
			
			assertNotNull(value);
			
			System.out.println("Value:" + value);
		} catch (Exception e) // was IOException
		{
			System.err.println("Exception thrown: " + e.getMessage());
		}
	}

}
