/*
 * BrightInsight CONFIDENTIAL

 * Copyright (c) 2019-2021 BrightInsight, All Rights Reserved.
 * NOTICE: These materials, together with all information, code, and other content contained herein (all of the
 * foregoing, collectively, this “Content”) is, and remains the property of BrightInsight, Inc. (“BrightInsight”), and
 * BrightInsight reserves all rights in and related to this Content. This Content is confidential and proprietary to
 * BrightInsight and may be covered by U.S. and/or foreign registered intellectual property or proprietary rights and/or
 * laws, including without limitation trade secret and copyright laws. Dissemination or reproduction of or access to
 * this Content, in whole or in part, is strictly forbidden unless prior written permission is obtained from
 * BrightInsight. The copyright notice above does not evidence any actual or intended publication or disclosure of this
 * Content, and this Content may be a trade secret of BrightInsight.

 * ANY USE, REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC PERFORMANCE, OR PUBLIC DISPLAY OF THIS CONTENT OR THROUGH
 * USE OF ANY SOFTWARE THAT IS PART OF THIS CONTENT (REGARDLESS OF WHETHER IN SOURCE OR OBJECT CODE), IN WHOLE OR IN
 * PART, IS STRICTLY PROHIBITED OTHER THAN AS EXPRESSLY AUTHORIZED BY BRIGHTINSIGHT IN WRITING, AND MAY BE IN VIOLATION
 * OF APPLICABLE LAWS AND INTERNATIONAL TREATIES. THE RECEIPT OR POSSESSION OF THIS CONTENT AND/OR RELATED INFORMATION
 * DOES NOT CONVEY OR IMPLY ANY RIGHT TO REPRODUCE, DISCLOSE, DISTRIBUTE OR OTHERWISE USE IT, OR TO MANUFACTURE, USE, OR
 * SELL ANYTHING THAT IT MAY DESCRIBE, IN WHOLE OR IN PART.
 */

package com.icesi.edu.users.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.NamedInheritableThreadLocal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class SecurityContextHolder {

    private static final String THREAD_NAME = "SECURITY_CONTEXT";
    private static final NamedInheritableThreadLocal<SecurityContext> contextHolder = new NamedInheritableThreadLocal<>(THREAD_NAME) ;

    /**
     * Remove the value of the actual context holder
     */
    public static void clearContext() {
        contextHolder.remove();
    }

    /**
     * Get the actual context, if it does not exist. create an empty one
     * @return {@link SecurityContext}
     */
    public static SecurityContext getContext() {
        SecurityContext ctx = contextHolder.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }
        return ctx;
    }



    /**
     * Given a {@link SecurityContext context} update the actual context
     * @param context a {@link SecurityContext} instance
     */
    public static void setUserContext(SecurityContext context) {
        if (context != null) {
            contextHolder.set(context);
        }
    }

    public static SecurityContext createEmptyContext() {
        return new SecurityContext();
    }

}