package de.femtopedia.legacyforgej8.transformers.asm4warn;

import de.femtopedia.legacyforgej8.MiniPlusTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("cpw.mods.fml.relauncher.CoreModManager$FMLPluginWrapper")
public class FMLPluginWrapperTransformer extends MiniPlusTransformer {

    @Patch.Method("injectIntoClassLoader(Lnet/minecraft/launchwrapper/LaunchClassLoader;)V")
    public void openWarnDialogIfNeeded(PatchContext ctx) {
        ctx.jumpToStart();

        ctx.add(
                ALOAD(1),
                INVOKESTATIC("de/femtopedia/legacyforgej8/transformers/asm4warn/Warnings",
                        "warnIfNeeded", "(Ljava/lang/ClassLoader;)V")
        );
    }

}
