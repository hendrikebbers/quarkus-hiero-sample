package org.hiero.spring;

import com.hedera.hashgraph.sdk.TokenId;
import com.openelements.hiero.base.HieroException;
import com.openelements.hiero.base.NftClient;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Singleton
@Path("/")
public class ViewController {

    private final Template index;

    private final NftClient nftClient;

    private final TokenId nftType;

    @Inject
    public ViewController(@Location("index") final Template index, final NftClient nftClient) {
        this.index = index;
        this.nftClient = nftClient;
        try {
            nftType = nftClient.createNftType("Quarkus-Sample", "QS");
        } catch (HieroException e) {
            throw new RuntimeException("Error in creating token", e);
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index() {
        return index.instance();
    }

    @POST
    @Path("/mint")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance mint() {
        return index.instance();
    }

    @POST
    @Path("/burnall")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance burnall() {
        return index.instance();
    }
}
