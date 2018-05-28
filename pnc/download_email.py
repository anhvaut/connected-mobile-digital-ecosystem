#!/usr/bin/python


import email, getpass, imaplib, os, subprocess, smtplib, socket
from email.MIMEMultipart import MIMEMultipart
from email.MIMEBase import MIMEBase
from email.MIMEText import MIMEText
from email import Encoders

hostname = socket.gethostname()
#detach_dir = '/home/~edou/test' # directory where to save attachments (default: current)
detach_dir = '/home/'+str(hostname)+'/Downloads/emaildownload'
log_dir = '/home/'+str(hostname)+'/Downloads/emaildownload'
# user = raw_input("Enter your GMail username:")
# pwd = getpass.getpass("Enter your password: ")


user = str(hostname)
print "user ="+user
pwd  = "hollyzhu"

def send_mail(to, subject, text, attach):
   msg = MIMEMultipart()

   msg['From'] = user
   msg['To'] = to
   msg['Subject'] = subject

   msg.attach(MIMEText(text))

   if attach:
       part = MIMEBase('application', 'octet-stream')
       part.set_payload(open(attach, 'rb').read())
       Encoders.encode_base64(part)
       part.add_header('Content-Disposition',
               'attachment; filename="%s"' % os.path.basename(attach))
       msg.attach(part)

   mailServer = smtplib.SMTP("smtp.gmail.com", 587)
   mailServer.ehlo()
   mailServer.starttls()
   mailServer.ehlo()
   mailServer.login(user, pwd)
   mailServer.sendmail(user, to, msg.as_string())
   # Should be mailServer.quit(), but that crashes...
   mailServer.close()



# connecting to the gmail imap server
m = imaplib.IMAP4_SSL("imap.gmail.com")
m.login(user,pwd)

m.select(user)

#m.select("[Gmail]/All Mail") # here you a can choose a mail box like INBOX instead
# m.select("[Gmail]/Inbox") # here you a can choose a mail box like INBOX instead
cond = "true"
# use m.list() to get all the mailboxes


#keywordsSearch = '';
#searchString = "(ALL SUBJECT '%s')" % keywordsSearch
searchString = "(SENTSINCE 16-MAR-2011)"

# `resp, items = m.search(None, "ALL") # you could filter using the IMAP rules here (check http://www.example-code.com/csharp/imap-search-critera.asp)
#resp, items = m.search("SENTSINCE 09-Mar-2010") # you could filter using the IMAP rules here (check http://www.example-code.com/csharp/imap-search-critera.asp)
# resp, items = m.search(None, "ALL") # you could filter using the IMAP rules here (check http://www.example-code.com/csharp/imap-search-critera.asp)
resp, items = m.search(None,searchString) # you could filter using the IMAP rules here (check http://www.example-code.com/csharp/imap-search-critera.asp)
items = items[0].split() # getting the mails id
cmdToExe = "none"
upgrade = "none"
for emailid in items:
    resp, data = m.fetch(emailid, "(RFC822)") # etching the mail, "`(RFC822)`" means "get the whole stuff", but you can ask for headers only, etc
    email_body = data[0][1] # getting the mail content
    mail = email.message_from_string(email_body) # parsing the mail content to get a mail object

    #Check if any attachments at all
    if mail.get_content_maintype() != 'multipart':
        continue

    print "["+mail["From"]+"] :" + mail["Subject"]
 
    sub1 = mail["Subject"]
 

    # we use walk to create a generator so we can iterate on the parts and forget about the recursive headach
    for part in mail.walk():
        # multipart are just containers, so we skip them
        if part.get_content_maintype() == 'multipart':
            continue

        # is this part an attachment ?
        if part.get('Content-Disposition') is None:
            continue

        sub_dir = os.path.join(detach_dir, sub1)
        print sub_dir

        if not os.path.exists(sub_dir):
           os.makedirs(sub_dir)

        filename = part.get_filename()

        counter = 1

        # if there is no filename, we create one with a counter to avoid duplicates
        if not filename:
            filename = 'part-%03d%s' % (counter, 'bin')
            counter += 1

        att_path = os.path.join(detach_dir, sub_dir, filename)
        upgrade = sub_dir
        cmdToExe = 'tar xzvf '+  filename

        #Check if its already there
        if not os.path.isfile(att_path) :
            # finally write the stuff
            fp = open(att_path, 'wb')
            fp.write(part.get_payload(decode=True))
            fp.close()
    m.store(emailid,'+FLAGS','(\Deleted)')
result = "none"
if cmdToExe != "none" :
   if upgrade != "none" :
       cmd = "cd "+upgrade+";echo '"+ cmdToExe+"' ; " + cmdToExe+ "; exit 0"
#       result=subprocess.call(cmd, shell=True)
       result = subprocess.check_output(['/bin/sh','-c',cmd],stderr=subprocess.STDOUT)
       print result
   send_mail("edou@uow.edu.au",
        "Result from download_email",
        result,
        ""
        )
