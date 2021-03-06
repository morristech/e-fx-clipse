package at.bestsolution.efxclipse.tooling.rrobot.impl.generators

import at.bestsolution.efxclipse.tooling.rrobot.model.task.Generator
import at.bestsolution.efxclipse.tooling.rrobot.model.bundle.ManifestFile
import java.util.Map
import java.io.ByteArrayInputStream
import at.bestsolution.efxclipse.tooling.rrobot.model.bundle.BundlePackage
import at.bestsolution.efxclipse.tooling.rrobot.model.bundle.RequiredBundle
import at.bestsolution.efxclipse.tooling.rrobot.model.bundle.ImportedPackage
import at.bestsolution.efxclipse.tooling.rrobot.model.bundle.ExportedPackage
import at.bestsolution.efxclipse.tooling.rrobot.model.bundle.BundleProject

class BundleManifestGenerator implements Generator<ManifestFile> {
	

	override generate(ManifestFile file, Map<String,Object> data) {
		return new ByteArrayInputStream(generateContent(file,data).toString().bytes);
	}
	
	def generateContent(ManifestFile file, Map<String,Object> data) '''Manifest-Version: 1.0
Bundle-ManifestVersion: 2
Bundle-Name: «file.bundlename»
Bundle-SymbolicName: «file.symbolicname»«IF (file.eContainer as BundleProject).pluginxml != null»; singleton:=true«ENDIF»
Bundle-Version: «file.version»
Bundle-RequiredExecutionEnvironment: «file.executionEnvironment»
«IF !file.requiredBundles.empty»
Require-Bundle: «file.requiredBundles.map() [ requireBundleBuilder ].join(",\r\n ")»
«ENDIF»
«IF !file.importedPackages.empty»
Import-Package: «file.importedPackages.map() [ importPackageBuilder ].join(",\r\n ")»
«ENDIF»
«IF !file.exportedPackages.empty»
Export-Package: «file.exportedPackages.map() [ exportPackageBuilder ].join(",\r\n ")»
«ENDIF»
	'''
	
	def exportPackageBuilder(ExportedPackage e) {
		var rv = e.name;
		if( e.eIsSet(BundlePackage$Literals::EXPORTED_PACKAGE__VERSION) ) {
			rv = rv.concat(";version=\""+e.eGet(BundlePackage$Literals::EXPORTED_PACKAGE__VERSION)+"\"");
		}
		return rv;
	}
	
	def importPackageBuilder(ImportedPackage i) {
		var rv = i.name;
		if(i.eIsSet(BundlePackage$Literals::IMPORTED_PACKAGE__MIN_VERSION) || i.eIsSet(BundlePackage$Literals::IMPORTED_PACKAGE__MAX_VERSION)) {
			rv = rv.concat(";version=\"");
			if (!i.eIsSet(BundlePackage$Literals::IMPORTED_PACKAGE__MAX_VERSION)) {
				rv = rv.concat(i.eGet(BundlePackage$Literals::IMPORTED_PACKAGE__MIN_VERSION).toString);
			} else {
				if( i.minExclusive ) {
					rv = rv.concat("(");
				} else {
					rv = rv.concat("[");
				}
				rv = rv.concat(i.eGet(BundlePackage$Literals::IMPORTED_PACKAGE__MIN_VERSION).toString);
				if( i.maxExclusive ) {
					rv = rv.concat(")");
				} else {
					rv = rv.concat("]");
				}
			}
			rv = rv.concat( "\""); 
		}
		return rv;
	}
	
	def requireBundleBuilder(RequiredBundle r) {
		var rv = r.name; 
		if(r.eIsSet(BundlePackage$Literals::REQUIRED_BUNDLE__MIN_VERSION) || r.eIsSet(BundlePackage$Literals::REQUIRED_BUNDLE__MAX_VERSION)) {
			rv = rv.concat("bundle-version=\"");
			if (!r.eIsSet(BundlePackage$Literals::REQUIRED_BUNDLE__MAX_VERSION)) {
				rv = rv.concat(r.eGet(BundlePackage$Literals::REQUIRED_BUNDLE__MIN_VERSION).toString);
			} else {
				if( r.minExclusive ) {
					rv = rv.concat("(");
				} else {
					rv = rv.concat("[");
				}
				rv = rv.concat(r.eGet(BundlePackage$Literals::REQUIRED_BUNDLE__MIN_VERSION).toString);
				if( r.maxExclusive ) {
					rv = rv.concat(")");
				} else {
					rv = rv.concat("]");
				}
			}
			rv = rv.concat( "\""); 
		}  
		return rv;
	}
}