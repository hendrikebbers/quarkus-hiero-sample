package org.hiero.spring;

import com.hedera.hashgraph.sdk.TokenId;
import com.openelements.hiero.base.HieroContext;
import com.openelements.hiero.base.HieroException;
import com.openelements.hiero.base.NftClient;
import com.openelements.hiero.base.mirrornode.NftRepository;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@Singleton
public class ViewController {

    @Inject
    private Template index;

    @Inject
    private NftClient nftClient;

    private TokenId nftType;

    @PostConstruct
    public void init() {
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
}
