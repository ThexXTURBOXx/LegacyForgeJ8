package de.femtopedia.legacyforgej8.transformers.api;

import de.femtopedia.legacyforgej8.MiniPlusTransformer;
import nilloader.api.lib.asm.tree.LabelNode;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("cpw.mods.fml.relauncher.RelaunchClassLoader")
public class RelaunchClassLoaderTransformer extends MiniPlusTransformer {

    @Patch.Method("<init>([Ljava/net/URL;)V")
    public void injectAPI(PatchContext ctx) {
        ctx.jumpToLastReturn();

        ctx.add(
                ALOAD(0),
                INVOKESTATIC("de/femtopedia/legacyforgej8/api/API", "registerExclusions",
                        "(Lcpw/mods/fml/relauncher/RelaunchClassLoader;)V")
        );
    }

    //@Patch.Method.AffectsControlFlow // Frames are recomputed in JsrInstructionTransformer
    @Patch.Method("findClass(Ljava/lang/String;)Ljava/lang/Class;")
    public void injectCLRegexAPI(PatchContext ctx) {
        LabelNode Lreturn = new LabelNode();

        ctx.search(
                ALOAD(0),
                GETFIELD("cpw/mods/fml/relauncher/RelaunchClassLoader", "classLoaderExceptions", "Ljava/util/Set;"),
                INVOKEINTERFACE("java/util/Set", "iterator", "()Ljava/util/Iterator;")
        ).jumpBefore();
        ctx.add(
                ALOAD(1),
                INVOKESTATIC("de/femtopedia/legacyforgej8/api/API", "matchesClassLoaderExclusionRegex",
                        "(Ljava/lang/String;)Z"),
                IFNE(Lreturn)
        );

        ctx.search(
                INVOKEVIRTUAL("java/lang/String", "startsWith", "(Ljava/lang/String;)Z"),
                IFEQ(null)
        ).jumpAfter();
        ctx.add(
                Lreturn
        );
    }

    //@Patch.Method.AffectsControlFlow // Frames are recomputed in JsrInstructionTransformer
    @Patch.Method("findClass(Ljava/lang/String;)Ljava/lang/Class;")
    public void injectTFRegexAPI(PatchContext ctx) {
        LabelNode Lreturn = new LabelNode();

        ctx.search(
                ALOAD(0),
                GETFIELD("cpw/mods/fml/relauncher/RelaunchClassLoader", "transformerExceptions", "Ljava/util/Set;"),
                INVOKEINTERFACE("java/util/Set", "iterator", "()Ljava/util/Iterator;")
        ).jumpBefore();
        ctx.add(
                ALOAD(1),
                INVOKESTATIC("de/femtopedia/legacyforgej8/api/API", "matchesTransformerExclusionRegex",
                        "(Ljava/lang/String;)Z"),
                IFNE(Lreturn)
        );

        ctx.search(
                INVOKEVIRTUAL("java/lang/String", "startsWith", "(Ljava/lang/String;)Z"),
                IFEQ(null)
        ).jumpAfter();
        ctx.add(
                Lreturn
        );
    }

}
