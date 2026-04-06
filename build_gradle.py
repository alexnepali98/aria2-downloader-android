#!/usr/bin/env python3
"""
Direct Gradle invocation wrapper for GitHub Actions
Avoids shell escaping issues by invoking gradlew directly via Python subprocess
"""

import subprocess
import sys
import os

def main():
    os.chdir(os.path.dirname(os.path.abspath(__file__)))
    
    # Make gradlew executable
    os.chmod("gradlew", 0o755)
    
    # Invoke gradle directly with subprocess
    # This avoids any shell escaping issues
    cmd = ["./gradlew", "assembleDebug"]
    
    print(f"Executing: {' '.join(cmd)}")
    result = subprocess.run(cmd, cwd=os.getcwd())
    
    sys.exit(result.returncode)

if __name__ == "__main__":
    main()
