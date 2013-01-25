#!/usr/bin/perl
use CGI;
use CGI::Carp qw ( fatalsToBrowser warningsToBrowser );
use warnings;
use File::Copy;
use lib ("/usr/nma/lib");
use NmaUtil;
$| = 1;  $ENV{"PATH"}="/bin:/sbin:/usr/bin:/usr/sbin";
$<=$>;

my $action = $ARGV[0];
my $path = $ARGV[1];
my $status = 0;   
NmaUtil::ilog("calling installScript.pl $action $path\n");
if ($action eq "add") {
	NmaUtil::ilog( " adding application");
	$status = &addApp($path);
}elsif( $action eq "upgrade" ) {
	print "upgrade application\n";
	my $vold = $ARGV[2];
	my $vnew = $ARGV[3];
 	$status = &upgradeApp($path, $vold, $vnew);
}else {
	NmaUtil::ilog("Invalid Action: $action\n");
	$status = 1;
}
	

if ($status == 0) {
	print "SUCCESS";
}else {
	print "FAILURE";
}
sub addApp {
	my $path = shift;
	# create file system if appropriate
	# start specific processes if appropriate
	# add firewall rules if appropriate
	# add url related information if appropriate
	# install rpm
	system ("rpm -i $path"."/*.rpm  > /dev/null");
	NmaUtil::ilog("addApp: add rpm complete\n");	
	return 0;
}

sub upgradeApp {
	my $path = shift;
	my $vo = shift;
	my $vn = shift;
	NmaUtil::ilog( "upgrading from $vo to $vn\n");
			system ("rpm -Uvh --allfiles --force --nopreun $path"."/*.rpm  > /dev/null");
	system ("sh $path"."/migrateDB.sh  $path"."/migrateDB.sql  > /dev/null ");
	
	return 0;
}
