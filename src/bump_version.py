from pathlib import Path
import argparse
import sys
import re


def non_empty_str(value: str) -> str:
	if len(value.strip()) == 0:
		raise ValueError
	return value


parser = argparse.ArgumentParser()
parser.add_argument("-p", "--project", required=True, type=non_empty_str, 
	help="Name of micro")
parser.add_argument("-v", "--version", type=non_empty_str,
	help="Set the version of the micro (--project) to a designed one")

args = parser.parse_args()

p = Path(args.project)
if not p.exists():
	print(f"Project {args.project} not found")
	sys.exit(1)

with open("docker-compose.yml", "r") as f:
	compose = f.read()

artifact = re.findall(fr"{args.project}-.*?\.jar", compose)
if not artifact:
	print(f"Project version {args.project} not found in compose")
	sys.exit(1)

artifact = artifact[0]

pom_xml = p.joinpath("pom.xml")
if not pom_xml.exists():
	print(f"Project {args.project}/pom.xml not found")
	sys.exit(1)

with open(pom_xml, "r") as f:
	pom = f.read()

prev_version = artifact[len(args.project):-4]
prev_version = prev_version.strip("-").strip("v")

pom_artifact = re.findall(f"<version>{prev_version}</version>", pom)
if not pom_artifact:
	print(f"Project {args.project} version {prev_version} not found in pom")
	sys.exit(1)

if args.version is None:
	print(args.project, prev_version)
	sys.exit(0)

pom_artifact = pom_artifact[0]
new_pom_artifact = f"<version>{args.version}</version>"
new_artifact = f"{args.project}-{args.version}.jar"

print(args.project)
print(pom_artifact, "->", new_pom_artifact)
print(artifact.ljust(len(pom_artifact), " "), "->", new_artifact)

with open("docker-compose.yml", "w") as f:
	f.write(compose.replace(artifact, new_artifact))

with open(pom_xml, "w") as f:
	f.write(pom.replace(pom_artifact, new_pom_artifact))
