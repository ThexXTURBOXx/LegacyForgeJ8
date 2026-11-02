package de.femtopedia.legacyforgej8.transformers.miscperipherals;

import de.femtopedia.legacyforgej8.MiniPlusTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.PatchContext.SearchResult;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("miscperipherals.asm.BlockTurtleTransformer")
public class BlockTurtleTransformerTransformer extends MiniPlusTransformer {

    @Patch.Method("transform([B)[B")
    public void useASM5Opcode(PatchContext ctx) {
        while (true) {
            SearchResult res = ctx.search(
                    NEW("org/objectweb/asm/tree/MethodNode"),
                    DUP(),
                    ICONST_4()
            );
            if (!res.isSuccessful()) break;
            res.jumpAfter();
            ctx.add(
                    POP(),
                    LDC(327680)
            );
        }
    }

}
