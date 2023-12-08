const fs = require('fs');
const path = require('path');

// Define the paths
const gitPropsPath = path.join(__dirname, '../../backend/build/path-to-git-properties/git.properties');
const outputPath = path.join(__dirname, 'path-to-react-app/src/gitInfo.json');

// Read the git.properties file
const gitPropsContent = fs.readFileSync(gitPropsPath, 'utf8');

// Extract values
let gitInfo = {};
gitPropsContent.split('\n').forEach(line => {
  const [key, value] = line.split('=');
  if (key && value) {
    gitInfo[key.trim()] = value.trim();
  }
});

// Write to a JSON file
fs.writeFileSync(outputPath, JSON.stringify(gitInfo, null, 2));
