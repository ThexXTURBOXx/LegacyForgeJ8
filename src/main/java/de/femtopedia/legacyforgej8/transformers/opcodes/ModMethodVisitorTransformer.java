package de.femtopedia.legacyforgej8.transformers.opcodes;

import de.femtopedia.legacyforgej8.MiniPlusTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("cpw.mods.fml.common.discovery.asm.ModMethodVisitor")
public class ModMethodVisitorTransformer extends MiniPlusTransformer {

    @Patch.Method.Optional
    @Patch.Method("<init>(Ljava/lang/String;Lcpw/mods/fml/common/discovery/asm/ASMModParser;)V")
    public void useASM5OpcodeOld(PatchContext ctx) {
        // This is the constructor in old FML versions
        migrateASM4ToASM5(ctx);
    }

    @Patch.Method.Optional
    @Patch.Method("<init>(Ljava/lang/String;Ljava/lang/String;Lcpw/mods/fml/common/discovery/asm/ASMModParser;)V")
    public void useASM5OpcodeNew(PatchContext ctx) {
        // Newer FML versions include this constructor instead
        migrateASM4ToASM5(ctx);
    }

}
