package com.kodecircle.circlefutsalprovider.core;

/**

 * This is the source code of CircleFutsal for Android ver.1.x.x.

 * It is licensed under GNU GPL v.3.

 * You should have received a copy of the license in this archive.

 * <p>

 * See license at :

 * https://github.com/ryanbekhen/circle_futsal/blob/master/LICENSE"

 * Copyright ACHMAD IRIANTO EKA PUTRA, 2018.

 */


import com.kodecircle.circlefutsalprovider.BuildConfig;

/**

 *  @author ACHMAD IRIANTO EKA PUTRA

 *  @version 1.0.1

 *  @since 25/01/18

 */



public class AppInfo {



    public static final boolean isSandboxMode = true;



    /**

     * The method for get version name and version code of application.

     * @return String of version name and version code.

     */

    public static String version() {

        return BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")";

    }

}