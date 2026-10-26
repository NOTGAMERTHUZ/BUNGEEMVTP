MHop beginner project — read before installing

IMPORTANT:
- This is source code, not a tested ready-made plugin.
- Do NOT install on a live server until GitHub Actions builds successfully and you have a backup.
- The same JAR is intended for the BungeeCord proxy, Lobby Paper server, and Main Paper server.
- BungeeCord server name in this project is "main".
- Main server must have Multiverse-Core installed.
- Check the world names in plugins/MHop/config.yml against /mv list.
- Keep AuthMe and all existing plugins in place while testing. Do not remove them as a troubleshooting shortcut.

GitHub upload:
1. Open your mhop repository.
2. Click Add file > Upload files.
3. Extract the ZIP on your computer first.
4. Open the extracted folder and upload the CONTENTS, including pom.xml, .github folder and src folder. Do not upload only the ZIP file.
5. Click Commit changes.
6. Open Actions and select Build MHop.
7. If it fails, open the failed run and copy the red error text.

When the build succeeds:
1. Open the successful workflow run.
2. At the bottom, download the MHop artifact ZIP.
3. Extract it to find MHop.jar.
4. Back up all three server folders before installation.
5. Stop Proxy, Lobby and Main.
6. Put MHop.jar in the plugins folder of Proxy, Lobby and Main.
7. Start them and check logs. Do not use /reload.
8. On Main, verify plugins/MHop/config.yml world names.
9. Configure DeluxeHub item actions on Lobby:
   Survival: [CLOSE], [MESSAGE] &aConnecting to Survival..., [CONSOLE] hop %player% survival
   PvP: [CLOSE], [MESSAGE] &6Connecting to PvP..., [CONSOLE] hop %player% pvp
   Lifesteal: [CLOSE], [MESSAGE] &cConnecting to Lifesteal..., [CONSOLE] hop %player% lifesteal
   Use each as separate quoted YAML list entries, matching the existing DeluxeHub file's indentation.
10. Test with OP first, then a non-admin account. If any error appears, stop and review the logs.

This source has not been compiled or integration-tested here. A successful build only proves compilation; test the actual proxy/backend messaging and Multiverse command on a backup or test setup before production.
