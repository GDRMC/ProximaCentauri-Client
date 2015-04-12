/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2015 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2015 Sun Microsystems, Inc.
 */
package gdr.proximacentauri.userdb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class UserDB {
    private ArrayList<User>users;
    
    public UserDB(File file) throws FileNotFoundException, IOException, NoSuchAlgorithmException{ 
        this.users=new ArrayList(); 
        readDB(new FileReader(file)); 
    }
    
    public boolean readDB(FileReader fic) throws IOException, NoSuchAlgorithmException
    {
        boolean end = false;
	StreamTokenizer entree = new StreamTokenizer(fic);
	if (entree.ttype ==  StreamTokenizer.TT_EOF){ return false ; }
        while(entree.ttype != StreamTokenizer.TT_EOF){
            entree.nextToken();
            String bufferU=entree.sval;
            if("!".equals(bufferU)){
                end = true;
                break;
            }else{
                entree.nextToken();
                String bufferP=entree.sval;
                addUserToDB(bufferU,bufferP);
            }
        }
        this.users.remove(this.users.size()-1);
        System.out.println("DB - LOADED "+this.users.size());
        return true;
    }
    
    public void addUserToDB(String bufferU, String bufferP) throws NoSuchAlgorithmException{
        this.users.add(new User(bufferU,bufferP));
        System.out.println("DB - ADDED "+bufferU+" WITH PASS HASH "+bufferP);
    }
    
    public String getUsername(int n){
        return this.users.get(n).getUsername();
    }
    
    public String getUserHash(int n){
        return this.users.get(n).getHashedPass();
    }
    
    public int search(String username){
        int indice=-1;
        for(int i=0;i<this.users.size();i++){
            if(this.users.get(i).getUsername().equals(username)){
                indice = i;
            }else{
                indice = -1;
            }
        }
        return indice;
    }
}
