package org.hiero.spring;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TokenId;
import com.openelements.hiero.base.HieroContext;
import com.openelements.hiero.base.HieroException;
import com.openelements.hiero.base.NftClient;
import com.openelements.hiero.base.data.Nft;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class ViewController {

    private final Template index;

    private final NftClient nftClient;

    private final TokenId nftType;

    private final AccountId operatorAccountId;

    @Inject
    public ViewController(@Location("index") final Template index, final NftClient nftClient, final HieroContext context) {
        this.index = index;
        this.nftClient = nftClient;
        this.operatorAccountId = context.getOperatorAccount().accountId();
        try {
            nftType = nftClient.createNftType("Quarkus-Sample", "QS");
        } catch (HieroException e) {
            throw new RuntimeException("Error in creating token", e);
        }
    }

    @GET
    public TemplateInstance index() throws HieroException {
        return index.data("nfts", getAllNftModels());
    }

    @POST
    @Path("/mint")
    public TemplateInstance mint() throws HieroException {
        String metadata = DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now());
        nftClient.mintNft(nftType, metadata.getBytes(StandardCharsets.UTF_8));
        return index.data("nfts", getAllNftModels());
    }

    @POST
    @Path("/burnall")
    public TemplateInstance burnall() throws HieroException {
        return index.data("nfts", getAllNftModels());
    }

    private Set<Nft> getAllNfts() throws HieroException {
        //TODO: NftRepository implemenetation for Microprofile is missing
        return Set.of();
    }

    private Set<NftModel> getAllNftModels() throws HieroException {
        return getAllNfts().stream()
                .map(nft -> new NftModel(nft.tokenId().toString(), Long.toString(nft.serial()),
                        new String(nft.metadata(), StandardCharsets.UTF_8)))
                .collect(Collectors.toSet());
    }

    public record NftModel(String name, String serial, String metadata) {
    }
}
