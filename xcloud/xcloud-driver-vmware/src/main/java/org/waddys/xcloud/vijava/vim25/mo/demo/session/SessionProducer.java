/*================================================================================
Copyright (c) 2008 VMware, Inc. All Rights Reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, 
this list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice, 
this list of conditions and the following disclaimer in the documentation 
and/or other materials provided with the distribution.

* Neither the name of VMware, Inc. nor the names of its contributors may be used
to endorse or promote products derived from this software without specific prior 
written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL VMWARE, INC. OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
POSSIBILITY OF SUCH DAMAGE.
================================================================================*/

package org.waddys.xcloud.vijava.vim25.mo.demo.session;

import java.net.URL;

import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.util.CommandLineParser;
import com.sugon.vim25.mo.util.OptionSpec;

/**
 * This demo shows how to get the session ID.
 * The session ID can be used by SessionConsumer.
 * @author sjin
 */

public class SessionProducer 
{
	public static void main(String[] args) throws Exception
	{
	    CommandLineParser clp = new CommandLineParser(new OptionSpec[]{}, args);
	   	String urlStr = clp.get_option("url");
  	    String username = clp.get_option("username");
	    String password = clp.get_option("password");

		ServiceInstance si = new ServiceInstance(new URL(urlStr), username, password, true);

		String sessionStr = si.getServerConnection().getSessionStr();
		
		System.out.println("sessionStr=" + sessionStr);

		Thread.sleep(30*60*1000);
		
		si.getServerConnection().logout();
	}

}