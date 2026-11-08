package de.femtopedia.legacyforgej8.transformers.java9;

import de.femtopedia.legacyforgej8.MiniPlusTransformer;
import java.util.zip.ZipEntry;
import nilloader.api.lib.asm.tree.AbstractInsnNode;
import nilloader.api.lib.asm.tree.LabelNode;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.PatchContext.SearchResult;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("cpw.mods.fml.common.discovery.JarDiscoverer")
public class JarDiscovererTransformer extends MiniPlusTransformer {

    @SuppressWarnings("deprecation")
    //@Patch.Method.AffectsControlFlow // Frames are recomputed in JsrInstructionTransformer
    @Patch.Method("discover(Lcpw/mods/fml/common/discovery/ModCandidate;Lcpw/mods/fml/common/discovery/ASMDataTable;)" +
                  "Ljava/util/List;")
    public void skipModuleInfos(PatchContext ctx) {
        LabelNode Lskip = new LabelNode();

        ctx.search(
                INVOKEVIRTUAL("java/util/ArrayList", "iterator", "()Ljava/util/Iterator;")
        ).jumpAfter();
        ctx.jumpForward(1); // Yes, this is safe. Also, this is more safe than the old approach...

        ctx.add(
                Lskip
        );

        SearchResult patternLoad = ctx.search(
                GETSTATIC("cpw/mods/fml/common/discovery/JarDiscoverer", "classFile", "Ljava/util/regex/Pattern;")
        );
        patternLoad.jumpAfter();

        AbstractInsnNode aload = ctx.get();

        patternLoad.jumpBefore();
        ctx.add(
                aload.clone(null),
                INVOKESTATIC(hooks(), "shouldSkipEntry", "(Ljava/util/zip/ZipEntry;)Z"),
                IFNE(Lskip)
        );
    }

    public static class Hooks {

        public static boolean shouldSkipEntry(ZipEntry ze) {
            // This is from "Vanilla Forge" - don't know if possible/needed
            if (ze.getName() == null) return true;

            // This is from "Vanilla Forge" - just here for 1.4.5 and earlier
            if (ze.getName().startsWith("__MACOSX")) return true;

            // Only supported with version 9+ - so we can safely ignore on Java 8
            if (ze.getName().startsWith("META-INF/versions/")) return true;

            // Safeguard for stray module infos
            if (ze.getName().endsWith("module-info.class")) return true;

            return false;
        }

    }

}
