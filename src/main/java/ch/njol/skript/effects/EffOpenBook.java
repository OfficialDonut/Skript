/**
 *   This file is part of Skript.
 *
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright Peter Güttinger, SkriptLang team and contributors
 */
package ch.njol.skript.effects;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Open Book")
@Description("Opens a written book to a player.")
@Examples("open book player's tool to player")
@Since("INSERT VERSION")
public class EffOpenBook extends Effect {
	
	static {
		Skript.registerEffect(EffOpenBook.class, "(open|show) book %itemtype% (to|for) %players%");
	}
	
	@SuppressWarnings("null")
	private Expression<ItemType> book;
	@SuppressWarnings("null")
	private Expression<Player> players;
	
	@SuppressWarnings({"unchecked", "null"})
	@Override
	public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult) {
		book = (Expression<ItemType>) exprs[0];
		players = (Expression<Player>) exprs[1];
		return true;
	}
	
	@Override
	protected void execute(final Event e) {
		@Nullable ItemType itemType = book.getSingle(e);
		if (itemType != null && itemType.getMaterial() == Material.WRITTEN_BOOK) {
			@Nullable ItemStack itemStack = itemType.getRandom();
			if (itemStack != null) {
				for (Player player : players.getArray(e)) {
					player.openBook(itemStack);
				}
			}
		}
	}
	
	@SuppressWarnings("null")
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "open book " + book.toString(e, debug) + " to " + players.toString(e, debug);
	}
	
}
