---
name: "reset-macos-project"
description: "Clean macOS ._* files, stop port 8080, delete target, and recompile. Invoke when project won't start due to ._* files or port issues."
---

# macOS Project Reset Skill

## What it does

This skill automatically cleans and resets your Spring Boot project when:
- macOS ._* temporary files cause compilation errors
- Port 8080 is already in use
- Target directory needs to be cleaned

## When to invoke

**Invoke this skill when:**
- You see errors about incompatible class files (._* files)
- Port 8080 is already in use
- Project won't start or compile properly
- You want to clean and reset everything

## What it does

1. **Stops any process** using port 8080
2. **Deletes** macOS ._* temporary files
3. **Removes** target directory
4. **Recompiles** project with Maven

## Usage

Just ask "reset my project" or "clean and rebuild" and this skill will execute the reset.sh script automatically.

## Manual command

If you want to run it manually:
```bash
./reset.sh
```
