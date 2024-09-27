package notai.client;

import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class HttpInterfaceUtil {
    public static <T> T createHttpInterface(RestClient restClient, Class<T> clazz) {
        HttpServiceProxyFactory build =
                HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return build.createClient(clazz);
    }
}
