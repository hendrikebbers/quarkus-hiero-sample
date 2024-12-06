package org.hiero.spring;

import com.hedera.hashgraph.sdk.TokenId;
import com.openelements.hiero.base.HieroContext;
import com.openelements.hiero.base.HieroException;
import com.openelements.hiero.base.NftClient;
import com.openelements.hiero.base.mirrornode.NftRepository;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class ViewController {

    @Inject
    private Template index;

    @Inject
    private HieroContext hieroContext;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index() {
        System.out.println(hieroContext.getOperatorAccount());
        return index.instance();
    }
}
