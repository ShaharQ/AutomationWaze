package com.automation.helpers;

/**
 * Created by OmriNissim on 06/03/2017.
 */

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinNT;

import static com.sun.jna.win32.W32APIOptions.DEFAULT_OPTIONS;//* https://jna.dev.java.net/ */
public interface Kernel32 extends W32API {
    Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class, DEFAULT_OPTIONS);
    /* http://msdn.microsoft.com/en-us/library/ms683179(VS.85).aspx */
    WinNT.HANDLE GetCurrentProcess();
    /* http://msdn.microsoft.com/en-us/library/ms683215.aspx */
    int GetProcessId(WinNT.HANDLE Process);
}
