package xie.common.utils.trust;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class XNoCheckX509HostnameVerifier implements HostnameVerifier {

	@Override
	public boolean verify(String arg0, SSLSession arg1) {
		return true;
	}

}
