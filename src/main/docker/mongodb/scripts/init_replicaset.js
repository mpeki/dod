var status = rs.status();
if(status.errmsg === 'no replset config has been received') {
    rs.initiate();
}
for (var i = 1; i <= param; i++) {
    if(i!==1)
        rs.add(folder+"_dod-mongodb-node_" + i + ":27017");
}
cfg = rs.conf();
cfg.members[0].host = folder+"_dod-mongodb-node_1:27017";
rs.reconfig(cfg);
