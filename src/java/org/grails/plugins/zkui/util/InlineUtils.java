/* Utils.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Fri Jul 20 19:30:17     2007, Created by tomyeh
}}IS_NOTE

Copyright (C) 2007 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under GPL Version 2.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
*/
package org.grails.plugins.zkui.util;

import org.grails.plugins.zkui.zul.Inline;
import org.grails.plugins.zkui.zul.Zk5Inline;
import org.zkoss.lang.Objects;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A utility  to
 * implement the capability to hold a list of children.
 *
 * @author tomyeh
 */
public class InlineUtils {
    private static final String MARK_PREFIX = "<zk~u[", MARK_POSTFIX = "]>";

    /**
     * Adjust the children based the output generated
     *
     * @param page   the page. Specified ony if parent is null (aka. root).
     * @param parent the parent component. If null, page must be specified.
     */
    public static void adjustChildren(Page page, Component parent,
                                      Collection<Component> children, String body) {


        Iterator it = new ArrayList<Component>(children).iterator();

        for (int j = 0, len = body != null ? body.length() : 0; j < len; ) {
            int k = body.indexOf(MARK_PREFIX, j);
            String txt = null;
            Component child = null;
            if (k >= 0) {
                int l = k + MARK_PREFIX.length();
                int m = body.indexOf(MARK_POSTFIX, l);
                if (m <= l) { //not found or empty uuid
                    k = -1;
                } else {
                    txt = body.substring(j, k).trim();
                    child = matchNext(it, body.substring(l, m));
                    k = m + MARK_POSTFIX.length();
                }
            }
            if (k < 0)
                txt = body.substring(j).trim();
            //inline handle...
            if (txt.length() > 0) {
                final Inline inl = new Inline(txt);
                if (child != null) {
                    if (parent != null)
                        parent.insertBefore(inl, child);
                    else
                        inl.setPageBefore(page, child);
                } else {
                    if (parent != null)
                        parent.appendChild(inl);
                    else
                        inl.setPage(page);
                }
            }

            if (k < 0) break; //no more
            j = k;
        }

//		while (it.hasNext())
//			((Component)it.next()).detach();
    }

    /**
     * Matches the next component with the specified uuid,
     * returns null if no match at all.
     * Note: if it.next().getUuid() is not uuid, it will be removed.
     */
    private static Component matchNext(Iterator it, String uuid) {
        while (it.hasNext()) {
            final Component child = (Component) it.next();
            if (Objects.equals(uuid, child.getUuid()))
                return child; //found
            // child.detach(); //remove it
            // Note: it assumes it has the full set and they are
            // in the same order
        }
        return null;
    }

    /**
     * Writes a special mark to the output to represent the component
     * of the specified child.
     * The mark is then used by {@link #adjustChildren}
     */
    /*package*/
    public static void writeComponentMark(Writer out, Component comp) throws IOException {
        out.write(MARK_PREFIX);
        out.write(comp.getUuid());
        out.write(MARK_POSTFIX);
    }

}
