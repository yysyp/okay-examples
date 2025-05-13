package ps.demo.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;


@Slf4j
@Component
public class CustHeaderRoutePredicateFactory extends
        AbstractRoutePredicateFactory<CustHeaderRoutePredicateFactory.Config> {

    public CustHeaderRoutePredicateFactory() {
        super(Config.class);
    }

    // ... setup code omitted
    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {
                log.info("exchange config={} uri={}, path={}", config, exchange.getRequest().getURI(), exchange.getRequest().getPath());

                return true;

            }
        };
    }

    //@Validated
    public static class Config {
        private boolean isGolden;
        private String customerIdCookie;
        public Config() {
            log.info("init Config");
        }
        public Config(boolean isGolden, String customerIdCookie ) {
            log.info("init Config");
            // ... constructor details omitted
            this.isGolden = isGolden;
            this.customerIdCookie = customerIdCookie;
        }

        public boolean isGolden() {
            return isGolden;
        }

        public void setGolden(boolean golden) {
            isGolden = golden;
        }

        public String getCustomerIdCookie() {
            return customerIdCookie;
        }

        public void setCustomerIdCookie(String customerIdCookie) {
            this.customerIdCookie = customerIdCookie;
        }
        // ...getters/setters omitted
    }
}
