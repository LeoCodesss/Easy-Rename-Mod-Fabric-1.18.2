package net.leocodes.easy_rename.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.Collection;
import java.util.Iterator;

public class RenameCommand {
    private static final SimpleCommandExceptionType FAILED_ENTITY_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.rename.failed.entity"));

    private static final SimpleCommandExceptionType FAILED_ITEMLESS_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.rename.failed.itemless"));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("rename").requires((source) -> {
            return source.hasPermissionLevel(2);
        })).then(CommandManager.argument("targets", EntityArgumentType.entities()).executes((context) -> {
                    return execute((ServerCommandSource)context.getSource(), EntityArgumentType.getEntities(context, "targets"), " ");})
                .then(CommandManager.argument("name", StringArgumentType.greedyString()).executes((context) -> {
                    return execute((ServerCommandSource)context.getSource(), EntityArgumentType.getEntities(context, "targets"), StringArgumentType.getString(context, "name"));
                }))));
    }

    private static int execute(ServerCommandSource source, Collection<? extends Entity> targets, String name) throws CommandSyntaxException {
        Iterator var5 = targets.iterator();
        while (var5.hasNext()) {
            Entity entity = (Entity) var5.next();
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                ItemStack itemStack = livingEntity.getMainHandStack();
                if (!itemStack.isEmpty()) {
                    itemStack.setCustomName(Text.of(name));
                } else if (targets.size() == 1) {
                    throw FAILED_ITEMLESS_EXCEPTION.create();
                }

            } else if(targets.size() == 1) {
                throw FAILED_ENTITY_EXCEPTION.create();
            }
        }
        return 1;
    }
}

