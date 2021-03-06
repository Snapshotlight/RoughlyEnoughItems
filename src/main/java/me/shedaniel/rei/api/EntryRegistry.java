/*
 * This file is licensed under the MIT License, part of Roughly Enough Items.
 * Copyright (c) 2018, 2019, 2020 shedaniel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.shedaniel.rei.api;

import me.shedaniel.rei.RoughlyEnoughItemsCore;
import me.shedaniel.rei.utils.CollectionUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Environment(EnvType.CLIENT)
public interface EntryRegistry {
    
    /**
     * @return the api instance of {@link me.shedaniel.rei.impl.EntryRegistryImpl}
     */
    static EntryRegistry getInstance() {
        return RoughlyEnoughItemsCore.getEntryRegistry();
    }
    
    /**
     * Gets the current modifiable stacks list
     *
     * @return a stacks list
     */
    List<EntryStack> getStacksList();
    
    List<EntryStack> getPreFilteredList();
    
    List<ItemStack> appendStacksForItem(Item item);
    
    /**
     * Gets all possible stacks from an item
     *
     * @param item the item to find
     * @return the array of possible stacks
     */
    ItemStack[] getAllStacksFromItem(Item item);
    
    default void registerEntry(EntryStack stack) {
        registerEntryAfter(null, stack);
    }
    
    /**
     * Registers an new stack to the entry list
     *
     * @param afterEntry the stack to put after
     * @param stack      the stack to register
     */
    default void registerEntryAfter(EntryStack afterEntry, EntryStack stack) {
        registerEntryAfter(afterEntry, stack, true);
    }
    
    /**
     * Registers an new stack to the entry list
     *
     * @param afterEntry           the stack to put after
     * @param stack                the stack to register
     * @param checkAlreadyContains whether the list should check if it is already on the list
     * @see #queueRegisterEntryAfter(EntryStack, Collection) for a faster method
     */
    @Deprecated
    @ApiStatus.Internal
    void registerEntryAfter(EntryStack afterEntry, EntryStack stack, boolean checkAlreadyContains);
    
    
    void queueRegisterEntryAfter(EntryStack afterEntry, Collection<? extends EntryStack> stacks);
    
    /**
     * Registers multiple stacks to the item list
     *
     * @param afterStack the stack to put after
     * @param stacks     the stacks to register
     */
    default void registerEntriesAfter(EntryStack afterStack, EntryStack... stacks) {
        registerEntriesAfter(afterStack, Arrays.asList(stacks));
    }
    
    /**
     * Registers multiple stacks to the item list
     *
     * @param afterStack the stack to put after
     * @param stacks     the stacks to register
     */
    void registerEntriesAfter(EntryStack afterStack, Collection<? extends EntryStack> stacks);
    
    /**
     * Registers multiple stacks to the item list
     *
     * @param stacks the stacks to register
     */
    default void registerEntries(EntryStack... stacks) {
        registerEntriesAfter(null, stacks);
    }
    
    /**
     * Checks if a stack is already registered
     *
     * @param stack the stack to check
     * @return whether the stack has been registered
     */
    default boolean alreadyContain(EntryStack stack) {
        return CollectionUtils.anyMatchEqualsAll(getStacksList(), stack);
    }
    
}
