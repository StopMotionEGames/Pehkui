package virtuoel.pehkui.mixin.compat117plus;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ShulkerEntity.class)
public class ShulkerEntityMixin {
	@Unique
	private static final double SAFETY_GAP = 0.01;

	@ModifyReturnValue(method = "calculateBoundingBox()Lnet/minecraft/util/math/Box;", at = @At("RETURN"))
	private Box pehkui$calculateBoundingBox(Box originalBox) {
		final ShulkerEntity entity = (ShulkerEntity) (Object) this;

		// Se não tiver escala, deixa o Vanilla cuidar
		final float widthScale = ScaleUtils.getBoundingBoxWidthScale(entity);
		final float heightScale = ScaleUtils.getBoundingBoxHeightScale(entity);
		if (widthScale == 1.0F && heightScale == 1.0F) return originalBox;

		BlockPos pos = entity.getBlockPos();
		Direction facing = entity.getAttachedFace().getOpposite();

		// 1. Calculamos o tamanho real que a caixa DEVE ter
		// O Shulker fechado tem 1.0 de largura e 1.0 de altura.
		// O progresso de abertura (0.0 a 1.0) aumenta a altura.
		double openProgress = entity.getOpenProgress(ScaleRenderUtils.getTickDelta(MinecraftClient.getInstance()));

		// Largura escalada (X e Z se estiver no chão)
		double scaledWidth = 1.0D * widthScale;
		// Altura escalada (Y se estiver no chão).
		// O 0.5D é a parte "móvel" do Shulker que abre.
		double scaledHeight = (1.0D + (openProgress * 0.5D)) * heightScale;

		// 2. Centro do bloco
		double cX = pos.getX() + 0.5D;
		double cY = pos.getY() + 0.5D;
		double cZ = pos.getZ() + 0.5D;

		// 3. Criamos a caixa baseada na escala (ainda sem o offset de face)
		double w = scaledWidth / 2.0D;

		double minX = cX - w, maxX = cX + w;
		double minY = cY - w, maxY = cY + w; // Usamos w aqui porque na parede a 'altura' vira 'largura'
		double minZ = cZ - w, maxZ = cZ + w;

		switch (facing) {
			case UP -> {
				minY = pos.getY();
				maxY = minY + scaledHeight;
			}
			case DOWN -> {
				maxY = pos.getY() + 1.0D;
				minY = maxY - scaledHeight;
			}
			case SOUTH -> {
				minZ = pos.getZ();
				maxZ = minZ + scaledHeight;
			}
			case NORTH -> {
				maxZ = pos.getZ() + 1.0D;
				minZ = maxZ - scaledHeight;
			}
			case EAST -> {
				minX = pos.getX();
				maxX = minX + scaledHeight;
			}
			case WEST -> {
				maxX = pos.getX() + 1.0D;
				minX = maxX - scaledHeight;
			}
		}

		return new Box(minX, minY, minZ, maxX, maxY, maxZ);
	}
}
