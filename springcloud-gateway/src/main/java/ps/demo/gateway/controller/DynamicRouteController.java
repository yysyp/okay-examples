package ps.demo.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ps.demo.gateway.config.DynamicRouteServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

/**
 * 查询网关的路由信息
 */
@RestController
@RequestMapping("/route")
public class DynamicRouteController {

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    //获取网关所有的路由信息
    @RequestMapping("/routes")
    public Flux<RouteDefinition> getRouteDefinitions() {
        return routeDefinitionLocator.getRouteDefinitions();
    }

    @RequestMapping("/updateRouteUri")
    public Mono<RouteDefinition> updateRouteUri(@RequestParam(name = "id") String id, @RequestParam(name = "uri", defaultValue = "https://www.12306.cn/") String uri) {
        Flux<RouteDefinition> definitionFlux = routeDefinitionLocator.getRouteDefinitions();

        //RouteDefinition routeDefinition = definitionFlux.filter(df -> id.equals(df.getId())).elementAt(0).block(Duration.ofSeconds(5));
        Mono<RouteDefinition> monoRd = definitionFlux.filter(df -> id.equals(df.getId())).elementAt(0);

        monoRd.subscribe((routeDefinition) -> {

            URI uriObj = null;
            if (uri.startsWith("http")) {
                uriObj = UriComponentsBuilder.fromHttpUrl(uri).build().toUri();
            } else {
                uriObj = URI.create(uri);
            }
            routeDefinition.setUri(uriObj);
            this.dynamicRouteService.update(routeDefinition);

        });

        return monoRd;
    }

}
