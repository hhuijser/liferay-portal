$dir = $CmdLine[1]

WinWait("Selenium", "", 10)
WinActivate("Selenium")
Sleep(2500)
Send("!t")
Sleep(1000)
Send("o")

WinWait("Options", "", 10)
WinActivate("Options")
Sleep(1000)
Send("!v")
Sleep(1000)
Send("!o")
Sleep(1000)
WinWait("Browse For Folder", "", 10)
WinActivate("Browse For Folder")
Sleep(1000)
Send("!f")
Sleep(1000)
Send($dir)
Sleep(1000)
Send("{ENTER}")
Sleep(1000)
Send("{TAB}")
Send("{TAB}")
Sleep(1000)
Send("{ENTER}")
Sleep(1000)