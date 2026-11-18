package de.femtopedia.legacyforgej8.transformers.asm4warn;

import de.femtopedia.legacyforgej8.MiniPlusTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("cpw.mods.fml.common.Loader")
public class LoaderTransformer extends MiniPlusTransformer {

    @Patch.Method("loadMods()V")
    public void openWarnDialogIfNeeded(PatchContext ctx) {
        ctx.jumpToStart();

        ctx.add(
                ALOAD(0),
                INVOKEVIRTUAL("java/lang/Object", "getClass", "()Ljava/lang/Class;"),
                INVOKEVIRTUAL("java/lang/Class", "getClassLoader", "()Ljava/lang/ClassLoader;"),
                INVOKESTATIC("de/femtopedia/legacyforgej8/transformers/asm4warn/Warnings",
                        "warnIfNeeded", "(Ljava/lang/ClassLoader;)V")
        );
    }

}
