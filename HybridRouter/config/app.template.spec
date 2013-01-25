%define cgidir /var/www/cgi-bin
%define version 1
%define dbName ${DBNAME}
%define earName ${APPNAME}
Name: JS${APPNAME}
Summary: ${SUMMARY}
Epoch: 0
Version: ${VERSION}
Release: ${RELEASE}
License: ${LICENSE}
Group: ${GROUP}
BuildRoot:  %{_tmppath}/%{name}-%{version}

%define spaceconfdir %{spaceconfroot}/%{name}

%description
%Module for Juniper

%prep
%setup -c -T

%build
find %{ear_location}../../bin/%{earName} -name urls.xml -exec cp '{}' . \;
find %{ear_location}../../bin/%{earName} -name sockets.xml -exec cp '{}' . \;
find %{ear_location} -name %{ear_file_name} -exec cp '{}' . \;
find %{ear_location}../../bin/%{earName} -name uninstallScript.pl -exec cp '{}' . \;
if [ %{dbName} != "null" ]; then
find %{ear_location}../../bin/%{earName} -name mysql-%{dbName}-ds.xml -exec cp '{}' . \;
find %{ear_location}../../bin -name db_script.sh -exec cp '{}' . \;
find %{ear_location}../../bin/%{earName} -name Cleanup_%{name}.sh -exec cp '{}' . \;
fi

%install
rm -rf $RPM_BUILD_ROOT
mkdir -p $RPM_BUILD_ROOT%{cgidir}
mkdir -p $RPM_BUILD_ROOT/%{jboss_deploy}/
mkdir -p $RPM_BUILD_ROOT/cgi-bin
mkdir -p $RPM_BUILD_ROOT%{spaceconfdir}

if [ %{dbName} != "null" ]; then
cp  %{ear_location}../../bin/db_script.sh $RPM_BUILD_ROOT%{spaceconfdir}
cp -f %{ear_location}../../bin/%{earName}/mysql-%{dbName}-ds.xml $RPM_BUILD_ROOT%{spaceconfdir}
fi

cp -f %{ear_location}../../bin/%{earName}/urls.xml $RPM_BUILD_ROOT%{spaceconfdir}
cp -f %{ear_location}../../bin/%{earName}/sockets.xml $RPM_BUILD_ROOT%{spaceconfdir}
cp %{ear_location}/%{ear_file_name} $RPM_BUILD_ROOT%{spaceconfdir}
cp -f %{ear_location}../../bin/%{earName}/Cleanup_%{name}.sh  $RPM_BUILD_ROOT/%{cgidir}/Cleanup_%{name}.sh

sed -e 's/<APPNAME>/%{name}/g' %{ear_location}../../bin/uninstallScript.pl >  $RPM_BUILD_ROOT/%{cgidir}/uninstall_%{earName}.pl
sed -e 's/<APPNAME>/%{name}/g' %{ear_location}../../bin/uninstallScript.pl >  $RPM_BUILD_ROOT/cgi-bin/uninstall_%{earName}.pl

%post

if [ "%{dbName}" != "null" ]; then
sh $RPM_BUILD_ROOT/%{spaceconfdir}/db_script.sh %{dbName} 

sleep 5

chmod 777 $RPM_BUILD_ROOT/%{spaceconfdir}/mysql-%{dbName}-ds.xml
chown root $RPM_BUILD_ROOT/%{spaceconfdir}/mysql-%{dbName}-ds.xml
chgrp root $RPM_BUILD_ROOT/%{spaceconfdir}/mysql-%{dbName}-ds.xml

mv $RPM_BUILD_ROOT/%{spaceconfdir}/mysql-%{dbName}-ds.xml $RPM_BUILD_ROOT/%{jboss_deploy}/

sleep 30

fi

chmod 777 $RPM_BUILD_ROOT/%{spaceconfdir}/%{ear_file_name}
chown root $RPM_BUILD_ROOT/%{spaceconfdir}/%{ear_file_name}
chgrp root $RPM_BUILD_ROOT/%{spaceconfdir}/%{ear_file_name}

mv $RPM_BUILD_ROOT/%{spaceconfdir}/%{ear_file_name} $RPM_BUILD_ROOT/%{jboss_deploy}/


%preun
echo "in pre uninstall " >> /tmp/log.error
rm -rf $RPM_BUILD_ROOT/%{jboss_deploy}/%{ear_file_name}
rm -rf $RPM_BUILD_ROOT/%{jboss_deploy}/mysql-%{dbName}-ds.xml

if [ "%{dbName}" != "null" ]; then
echo "Dropping database " >> /tmp/log.error 
/usr/bin/mysql -u root <<EOF
drop database %{dbName}_db;
EOF
fi

echo "DB dropped" >> /tmp/log.error


%files
%attr(4550, root, apache) /%{cgidir}/uninstall_%{earName}.pl
%attr(4550, root, apache) /cgi-bin/uninstall_%{earName}.pl
%attr(777, root, root)  /%{cgidir}/Cleanup_%{name}.sh
%attr(440, root, root) %{spaceconfdir}
